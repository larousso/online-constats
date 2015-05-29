package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Constat;
import play.Logger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.constat;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(""));
    }

    public static Result getConstat() {
        Form<Constat> form = Form.form(Constat.class);
        return ok(constat.render(form));
    }

    public static F.Promise<Result> createConstat() {
        Form<Constat> form = Form.form(Constat.class);
        Constat constat = form.bindFromRequest().get();
        String url = "http://localhost:9000/constats";
        return WS.url(url).post(Json.toJson(constat))
                .map(rep -> {
                    Result result;
                    Logger.debug("Code http "+rep.getStatus()+" "+rep.getStatusText());
                    switch (rep.getStatus()) {
                        case 200:
                            Logger.debug("Code 200");
                            result = redirect(controllers.routes.Application.index());
                            break;
                        case 400:
                            final JsonNode jsonNode = rep.asJson();
                            Logger.error("Erreur "+jsonNode.toString());
                            result = ok(views.html.constat.render(reject(form, jsonNode)));
                            break;
                        default:
                            form.reject("Une erreur est survenue");
                            result = ok(views.html.constat.render(form));
                            break;
                    }
                    return result;
                });
    }

    private static Form<Constat> reject(Form<Constat> form, JsonNode node) {
        node.fields().forEachRemaining(err -> {
            String path = err.getKey();
            JsonNode errors = err.getValue();
            errors.forEach(error -> form.reject(new ValidationError(path, error.asText())));
        });
        return form;
    }

}
