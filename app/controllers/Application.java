package controllers;

import akka.actor.ActorPath;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;
import views.html.login;

import java.util.HashMap;

import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Hallo"));
    }

    public static WebSocket<String> wstest() {
        return new WebSocket<String>() {

            // Wird beim initialen Verboindungsaufbau aufgerufen
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

                // For each event received on the socket,
                in.onMessage(event -> {
                    out.write("Message recieved:" + event);
                });

                // When the socket is closed.
                in.onClose(() -> System.out.println("Disconnected"));

            }

        };
    }

    static HashMap<String, ActorPath> list = new HashMap<>();

    public static WebSocket<String> wsactor() {
        String s = session().get("email");
        return WebSocket.withActor(out -> {
            list.put(s, out.path());
            return MyWebSocketActor.props(out);
        });
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
        String s = loginForm.get().email;
        session("email", s);
        System.out.println(s);
        if (s != null || s.equals(""))
            return redirect("/");
        return badRequest(login.render(form(Login.class)));
    }
}
