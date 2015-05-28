package models

import play.api.data.Forms._
import play.api.libs.json.Json


case class Vehicule(marque: String, modele: String)

object Vehicule {
  val mappingVehicule = mapping(
    "marque" -> text,
    "modele" -> text
  )(Vehicule.apply)(Vehicule.unapply)

  implicit val formatVehicule = Json.format[Vehicule]
}

case class Immatriculation(immatriculation: String, vehicule: Option[Vehicule])

object Immatriculation {

  import Vehicule._

  implicit val formatImmatriculation = Json.format[Immatriculation]

  val mappingImmatriculation = mapping(
    "immatriculation" -> text,
    "vehicule" -> optional(mappingVehicule)
  )(Immatriculation.apply)(Immatriculation.unapply)

}

