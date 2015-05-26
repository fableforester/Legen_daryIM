package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import model.Benutzer;
import model.BenutzerDao;


import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import play.mvc.Security;
import play.mvc.WebSocket;
import views.html.index;
import views.html.login;
import views.html.register;

import static play.data.Form.form;

public class Application extends Controller {

    public static BenutzerDao benutzerDao = new BenutzerDao();

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
        return WebSocket.withActor(out -> WebSocketActor.props(out, s));
    }

    /*
    Erstellt eine Form und gibt die LogIn Seite zurück
     */
    public static Result login() {
        return ok(
                login.render(form(BenutzerForm.class), "")
        );
    }

    /*
    Erstellt eine Form und gibt die Seite zum Registrieren zurück
     */
    public static Result register() {
        return ok(
                register.render(form(BenutzerForm.class), "Bitte Email und Passwort angeben")
        );
    }

    /*
    Erstellt einen Benutzer
     */
    public static Result registerBenutzer() {
        Form<BenutzerForm> registerForm = form(BenutzerForm.class).bindFromRequest();
        String name = registerForm.get().name;
        String email = registerForm.get().email;
        String password = registerForm.get().password;

        //Falls der Benutzer nicht exisitiert wird einer erstellt
        //Ansonsten die gleiche Seite mit einer Fehlermeldung angezeigt
        if (benutzerDao.registerUser(name, email, password)){
            return ok(
                    login.render(form(BenutzerForm.class), "Registrierung erfolgreich!")
            );
        } else {
            return ok(
                    register.render(form(BenutzerForm.class), "Email schon vergeben")
            );
        }
    }

    /*
    Authentifiziert den Benutzer und schreibt die email in die Session
     */
    public static Result authenticate() {
        Form<BenutzerForm> loginForm = form(BenutzerForm.class).bindFromRequest();
        String email = loginForm.get().email;
        String password = loginForm.get().password;

        //Falls Nameu nd Passwort stimmen wird dieser in die Session geschrieben
        if (benutzerDao.validateUser(email, password)){
            session().clear();
            session("email", email);
            //Wieterleitung an die Hauptseite
            return redirect("/");
        }
        return ok(login.render(form(BenutzerForm.class), "Falsches Passwort "));
    }

    /*
    Leert die Session und leitet zum LogIn weiter
     */
    public static Result logout(){
        session().clear();
        return redirect("/login");
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


    public static Result jsRoutes()
    {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("appRoutes", //appRoutes will be the JS object available in our view
                routes.javascript.Application.addFriend()
        ));
    }

    /*
    Hilfklasse für doe Formulare
     */
    public static class BenutzerForm {
        public String name;
        public String email;
        public String password;

    }

}
