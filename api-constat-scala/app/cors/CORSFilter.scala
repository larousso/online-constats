package cors

import controllers.Default
import play.api.Logger
import play.api.mvc.{Result, RequestHeader, Filter}

/**
 * CORS
 * Created by adelegue on 28/05/15.
 */
case class CORSFilter() extends Filter {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  lazy val allowedDomain = play.api.Play.current.configuration.getString("cors.allowed.domain")
  def isPreFlight(r: RequestHeader) =(
    r.method.toLowerCase.equals("options")
      &&
      r.headers.get("Access-Control-Request-Method").nonEmpty
    )

  def apply(f: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    Logger.trace("[cors] filtering request to add cors")
    if (isPreFlight(request)) {
      Logger.trace("[cors] request is preflight")
      Logger.trace(s"[cors] default allowed domain is $allowedDomain")
      Future.successful(Default.Ok.withHeaders(
        "Access-Control-Allow-Origin" -> allowedDomain.orElse(request.headers.get("Origin")).getOrElse(""),
        "Access-Control-Allow-Methods" -> request.headers.get("Access-Control-Request-Method").getOrElse("GET, POST, PUT, OPTIONS"),
        "Access-Control-Allow-Headers" -> request.headers.get("Access-Control-Request-Headers").getOrElse("Accept, Content-Type")
      ))
    } else {
      Logger.trace("[cors] request is normal")
      Logger.trace(s"[cors] default allowed domain is $allowedDomain")
      f(request).map{_.withHeaders(
        "Access-Control-Allow-Origin" -> allowedDomain.orElse(request.headers.get("Origin")).getOrElse(""),
        "Access-Control-Allow-Methods" -> request.headers.get("Access-Control-Request-Method").getOrElse("GET, POST, PUT, OPTIONS"),
        "Access-Control-Allow-Headers" -> request.headers.get("Access-Control-Request-Headers").getOrElse("*")
      )}
    }
  }
}
