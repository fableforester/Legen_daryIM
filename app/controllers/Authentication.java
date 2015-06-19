package controllers;

import model.BenutzerDao;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.register;

import static play.data.Form.form;

/**
 * Created by alexanderberg on 19.06.15.
 */
public class Authentication extends Controller {

    public static BenutzerDao benutzerDao = BenutzerDao.getInstance();

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
Hilfklasse für doe Formulare
 */
    public static class BenutzerForm {
        public String name;
        public String email;
        public String password;

    }
}
