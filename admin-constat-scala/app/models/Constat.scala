package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

case class Conducteur(nom: String, prenom: String, immatriculation: Immatriculation)

object Conducteur {

  implicit val formatConducteur = Json.format[Conducteur]

  val conducteurMapping = mapping(
    "nom" -> text,
    "prenom" -> text,
    "immatriculation" -> Immatriculation.mappingImmatriculation
  )(Conducteur.apply)(Conducteur.unapply)

}

case class Constat(conducteurA: Conducteur, conducteurB: Conducteur, description: String)

object Constat {

  import Conducteur._

  /** Json conversion */
  implicit val format = Json.format[Constat]

  /** Json form */
  val constatForm = Form(
    mapping(
      "conducteurA" -> conducteurMapping,
      "conducteurB" -> conducteurMapping,
      "description" -> text
    )(Constat.apply)(Constat.unapply)
  )


}

