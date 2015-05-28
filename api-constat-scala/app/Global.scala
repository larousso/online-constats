import cors.CORSFilter
import play.api.GlobalSettings
import play.api.mvc.WithFilters

/**
 * Created by adelegue on 28/05/15.
 */
object Global extends WithFilters(CORSFilter()) with GlobalSettings {

}
