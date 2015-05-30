package controllers

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.ActorFlowMaterializer
import akka.util.Timeout
import com.softwaremill.react.kafka.ReactiveKafka
import kafka.serializer.StringDecoder
import models.Constat
import org.reactivestreams.{Subscriber, Subscription}
import play.api.Logger
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}
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


  object WebSocketActor {
    def props(out: ActorRef) = Props(classOf[WebSocketActor], out)
  }

  class WebSocketActor(out: ActorRef) extends Actor {

    var subscriptionRef: Option[Subscription] = None

    /**
     * Démarrage de l'acteur
     */
    override def preStart(): Unit = {
      Logger.debug(s"Starting websocket actor")

      //On s'abonne au stream :
      kafka
       .consumeFromEnd("constats", "constats", new StringDecoder())
       .subscribe(new Subscriber[String] {

          override def onSubscribe(subscription: Subscription): Unit = {
            subscriptionRef = Some(subscription)
            Logger.debug(s"Request 1 message")
            subscriptionRef.foreach(_.request(1))
          }

          override def onComplete(): Unit = {
            Logger.debug(s"On complete")
          }

          override def onNext(t: String): Unit = {
            Logger.debug(s"Getting 1 message $t")
            out ! Json.parse(t)
            subscriptionRef.foreach(_.request(1))
          }

         override def onError(throwable: Throwable): Unit = {
           Logger.error(s"Error on kafka subscriber", throwable)
         }
     })
    }

    //Fermeture de la socket :
    override def postStop() = {
      subscriptionRef.foreach(_.cancel)
    }

    //Pas de messages entrant :
    override def receive: Receive = {
      case any => Logger.debug(s"Message ??? $any")
    }
  }


  def socket = WebSocket.acceptWithActor[JsValue, JsValue] { request => out =>
    WebSocketActor.props(out)
  }

}