package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.Benutzer;
import model.BenutzerDao;


import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

import play.mvc.Security;
import play.mvc.WebSocket;
import views.html.index;

import java.util.List;

import static play.data.Form.form;

public class Application extends Controller {

    public static BenutzerDao benutzerDao = BenutzerDao.getInstance();

    /*
    Erstellt die Hauptseite
     */
    @Security.Authenticated(Authenticator.class)
    public static Result index() {
        return ok(
                index.render(session("email"),
                        benutzerDao.getFriends(session().get("email")))
        );
    }

    /*
    Erstellt einen WebSocketActor welche die Anfragen vom Client entgegennimmt und
    Nachrichten an den Client schickt
     */
    public static WebSocket<String> webSocket() {
        String s = session().get("email");
        return WebSocket.withActor(out -> MessageWebSocket.props(out, s));
    }

    public static WebSocket<String> ConfirmationWebSocket() {
        String s = session().get("email");
        return WebSocket.withActor(out -> ConfirmationWebSocket.props(out, s));
    }

    /*
    Fügt den Freund hinzu und liefert den Namen zurück bei Erfolg
     */
    public static Result addFriend(String email) {
        if (benutzerDao.existUser(email)) {
            String emailSelf = session().get("email");
            Benutzer addedFriend = benutzerDao.createLink(emailSelf, email);
            //Passwort/Liste leeren damit dieser nicht an den Client gesendet wird
            addedFriend.setPasswort("");
            addedFriend.setKontaktliste(null);

            JsonNode addedFriednJson = play.libs.Json.toJson(addedFriend);
            return ok(addedFriednJson);
        }
        return badRequest();
    }

    public static Result getFriends() {
        List<Benutzer> friends = benutzerDao.getFriends(session("email"));
        return ok(play.libs.Json.toJson(friends).toString());
    }

    public static Result jsRoutes()
    {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("appRoutes", //appRoutes will be the JS object available in our view
                routes.javascript.Application.addFriend(),
                routes.javascript.Application.getFriends()
        ));
    }



}
