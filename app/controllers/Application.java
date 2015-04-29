package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;
import views.html.login;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(session("email")));
    }

    public static WebSocket<String> webSocket() {
        String s = session().get("email");
        return WebSocket.withActor(out -> WebSocketActor.props(out, s));
    }

    public static class Login {

        public String email;
        public String password;

    }

    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        String emailString = loginForm.get().email;
        session("email", emailString);
        System.out.println(emailString);
        if (emailString != null | emailString.equals(""))
            return redirect("/");
        return badRequest(login.render(form(Login.class)));
    }
}
