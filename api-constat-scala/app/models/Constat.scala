package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoPlugin._
import play.modules.reactivemongo.json.BSONFormats._
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONObjectID
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import tools.MongoMapping

import scala.concurrent.Future

case class Conducteur(nom: String, prenom: String, immatriculation: Immatriculation, circonstances: Circonstances)

object Conducteur {

  implicit val formatConducteur = Json.format[Conducteur]

  val conducteurMapping = mapping(
    "nom" -> nonEmptyText,
    "prenom" -> nonEmptyText,
    "immatriculation" -> Immatriculation.mappingImmatriculation,
    "circonstances" -> Circonstances.circonstanceMapping
  )(Conducteur.apply)(Conducteur.unapply)

}

case class Constat(_id: Option[BSONObjectID], conducteurA: Conducteur, conducteurB: Conducteur, description: String) {

  def save(): Future[Constat] = Constat.save(this)

  def loadVehicule(): Future[Constat] = {
    val fImmat1: Future[Immatriculation] = this.conducteurA.immatriculation.loadVehicule()
    val fImmat2: Future[Immatriculation] = this.conducteurB.immatriculation.loadVehicule()
    for {
      immat1 <- fImmat1
      immat2 <- fImmat2
    } yield {
      val conducteurA = this.conducteurA.copy(immatriculation = immat1)
      val conducteurB = this.conducteurB.copy(immatriculation = immat2)
      this.copy(conducteurA = conducteurA, conducteurB = conducteurB)
    }
  }
}

object Constat {

  import Conducteur._

  /** Json conversion */
  implicit val format = Json.format[Constat]

  /** Json form */
  val constatForm = Form(
    mapping(
      "_id" -> MongoMapping.mappingBsonId,
      "conducteurA" -> conducteurMapping,
      "conducteurB" -> conducteurMapping,
      "description" -> text
    )(Constat.apply)(Constat.unapply)
  )

  /** Services */
  val collection = db.collection[JSONCollection]("constats")

  def save(constat: Constat): Future[Constat] = {
    collection.save(constat).map(e => constat)
  }

  def list(): Future[List[Constat]] = {
    collection.find(Json.obj()).cursor[Constat].collect[List]()
  }

}

