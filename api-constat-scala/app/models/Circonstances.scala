package models

import play.api.data.Forms._
import play.api.libs.json.Json

case class Circonstances (
   cases2: Boolean = false,
   cases4: Boolean = false,
   cases8: Boolean = false,
   cases10: Boolean = false,
   cases14: Boolean = false,
   cases15: Boolean = false,
   cases16: Boolean = false
)

object Circonstances {

  implicit val circonstanceFormat = Json.format[Circonstances]

  val circonstanceMapping = mapping(
   "case2" -> boolean,
   "case4" -> boolean,
   "case8" -> boolean,
   "case10" -> boolean,
   "case14" -> boolean,
   "case15" -> boolean,
   "case16" -> boolean
  )(Circonstances.apply)(Circonstances.unapply)

}