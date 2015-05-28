package models

import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future


case class Vehicule(marque: String, modele: String)

object Vehicule {
  val mappingVehicule = mapping(
    "marque" -> text,
    "modele" -> text
  )(Vehicule.apply)(Vehicule.unapply)

  implicit val formatVehicule = Json.format[Vehicule]
}

case class Immatriculation(immatriculation: String, vehicule: Option[Vehicule]) {
  def loadVehicule(): Future[Immatriculation] = {
    val immat = this
    Immatriculation.getVehiculeByImmat(this.immatriculation).map(v => immat.copy(vehicule = v))
  }
}

object Immatriculation {

  import Vehicule._

  implicit val formatImmatriculation = Json.format[Immatriculation]

  val mappingImmatriculation = mapping(
    "immatriculation" -> text,
    "vehicule" -> optional(mappingVehicule)
  )(Immatriculation.apply)(Immatriculation.unapply)

  def getVehiculeByImmat(immat:String): Future[Option[Vehicule]] = {
    Future{
      vehicule.get(immat).flatMap(i => i.vehicule)
    }
  }

  val vehicule: Map[String, Immatriculation] = Seq(
      Immatriculation("12345AA85", Some(Vehicule("toyota", "yaris")))
    )
    .map(v => v.immatriculation -> v).toMap

}

