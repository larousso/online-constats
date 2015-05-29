package controllers

import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.Timeout
import com.softwaremill.react.kafka.ReactiveKafka
import kafka.serializer.StringDecoder
import models.Constat
import org.reactivestreams.Publisher
import play.api.Logger
import play.api.Play.current
import play.api.libs.EventSource
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee.Enumeratee
import play.api.libs.streams.Streams
import play.api.libs.ws.WS
import play.api.mvc._

import scala.concurrent.duration.DurationInt

object Application extends Controller {


  implicit val sys = ActorSystem("Constats")
  implicit val mat = ActorFlowMaterializer()
  implicit val timeout = Timeout(3.seconds)

  val host = "192.168.59.103"
  val kafkaPort = 9092
  val zookeeperPort = 2181
  val kafka = new ReactiveKafka(host = s"$host:$kafkaPort", zooKeeperHost = s"$host:$zookeeperPort")


  def index = Action.async {
    val server = "http://localhost:9000"
    WS.url(s"$server/constats").get().map { rep =>
      rep.status match {
        case 200 =>
          val constats: List[Constat] = rep.json.as[List[Constat]]
          Ok(views.html.index(constats.reverse))
        case _ =>
          Logger.error(s"Oups ${rep.statusText}")
          BadRequest("Oups")
      }
    }
  }




  def stream = Action {

    Logger.debug(s"stream")

    val kafkaPub: Publisher[String] = kafka.consumeFromEnd("constats", "constats", new StringDecoder())

    Ok.feed(
        Streams.publisherToEnumerator(kafkaPub).map{any => Logger.debug(s"$any");any}
          &> EventSource())
      .as("text/event-stream")
  }

}