package tools

import play.api.data.Forms._
import play.api.data.validation.Constraints._
import reactivemongo.bson.BSONObjectID
import play.api.data.format.Formats._

object MongoMapping {

  val mappingBsonId = mapping(
    "_id" -> optional(of[String] verifying pattern(
      """[a-fA-F0-9]{24}""".r,
      "constraint.objectId",
      "error.objectId"))
  ){ id =>
    id.map(BSONObjectID(_)) }
  { id =>
    Some(id.map(_.stringify))
  }

}
