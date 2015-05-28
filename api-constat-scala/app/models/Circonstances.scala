package models

import play.api.data.Forms._
import play.api.libs.json.Json

object Cases {

  sealed case class Case(num: Int, libelle: String)
  object Case2 extends Case(2, "quittait son stationnement")
  object Case4 extends Case(4, "sortait d’un parking, d’un lieu privé, d’un chemin de terre")
  object Case8 extends Case(8, "heurtait l’arrière en roulant dans le même sens et sur une même file")
  object Case10 extends Case(10, "changeait de file")
  object Case14 extends Case(14, "reculait")
  object Case15 extends Case(15, "Empiétait sur une voie réservée à la circulation en sens inverse")
  object Case16 extends Case(16, "n’avait pas observé le signal de sécurité")

  val caseMapping = mapping(
    "num" -> number,
    "libelle" -> text
  )(Case.apply)(Case.unapply)

}

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