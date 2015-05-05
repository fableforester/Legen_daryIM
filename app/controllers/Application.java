package controllers;

import model.BenutzerDao;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;
import views.html.login;
import views.html.register;

import static play.data.Form.form;

public class Application extends Controller {

    public static BenutzerDao benutzerDao = new BenutzerDao();

    public static Result index() {
        return ok(index.render(session("email"), benutzerDao.getFreunde()));
    }

    public static WebSocket<String> webSocket() {
        String s = session().get("email");
        return WebSocket.withActor(out -> WebSocketActor.props(out, s));
    }

    public static class Benutzer {

        public String email;
        public String password;

    }

    public static Result login() {
        return ok(
                login.render(form(Benutzer.class), "")
        );
    }

    public static Result register() {
        return ok(
                register.render(form(Benutzer.class), "Bitte Email und Passwort angeben")
        );
    }

    public static Result registerBenutzer() {
        Form<Benutzer> registerForm = form(Benutzer.class).bindFromRequest();
        String email = registerForm.get().email;
        String password = registerForm.get().password;

        if (benutzerDao.registerBenutzer(email,password)){
            return ok(
                    login.render(form(Benutzer.class), "Registrierung erfolgreich!")
            );
        }

        return ok(
                register.render(form(Benutzer.class),"")
        );

    }


    public static Result authenticate() {
        Form<Benutzer> loginForm = form(Benutzer.class).bindFromRequest();
        String email = loginForm.get().email;
        String password = loginForm.get().password;

        if (benutzerDao.validateBenutzer(email, password)){
            session().clear();
            session("email", email);
            return redirect("/");
        }
        return badRequest(login.render(form(Benutzer.class), "Falsches Passwort "));
    }

    public static Result logout(){
        session("email", "");
        return redirect("/login");
    }
}
