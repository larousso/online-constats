package controllers

import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.FlowGraph.Implicits._
import akka.stream.scaladsl._
import akka.util.Timeout
import com.softwaremill.react.kafka.ReactiveKafka
import kafka.serializer.StringEncoder
import models.Constat
import play.api.Logger
import play.api.http.HeaderNames
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }



  implicit val sys = ActorSystem("Constats")
  implicit val mat = ActorFlowMaterializer()
  implicit val timeout = Timeout(3.seconds)

  val host = "192.168.59.103"
  val kafkaPort = 9092
  val zookeeperPort = 2181
  val kafka = new ReactiveKafka(host = s"$host:$kafkaPort", zooKeeperHost = s"$host:$zookeeperPort")

  /**
   * API de création de constat
   * @return
   */
  def newConstat = Action.async(parse.json) { implicit r =>
    Constat.constatForm.bindFromRequest.fold(
      errors => Future{
        BadRequest(errors.errorsAsJson)
      },
      constat => {

        Logger.debug(s"New Constat $constat")

        val resultSink = Sink.head[Constat]
        val kafkaSink = Sink.apply(kafka.publish("constats", "constats", new StringEncoder()))

        val graph = FlowGraph.closed(resultSink) { implicit b =>
          (headSink) =>

          val bcast = b.add(Broadcast[Constat](2))

          Source.single(constat)
            .mapAsync(1)(_.loadVehicule()) ~> bcast.in

          bcast.out(0) ~> Flow[Constat].mapAsync(1)(c => c.save()) ~> headSink
          bcast.out(1) ~> Flow[Constat].map(c => Json.stringify(Json.toJson(c))) ~> kafkaSink
        }

        val updatedConstat: Future[Constat] = graph.run()
        updatedConstat
          .map{c =>
            Ok(Json.toJson(c))
          }
      }
    )
  }

  /**
   * Preflight CORS.
   *
   * @param all
   * @return
   */
  def preflight(all: String) = Action {
    Ok("")
  }

  /**
   * Api de liste de constats.
   *
   * @return
   */
  def listConstat() = Action.async {
    Constat.list().map(c => Ok(Json.toJson(c)))
  }


}