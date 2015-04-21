package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Hallod"));
    }

    public static WebSocket<String> wstest() {
        return new WebSocket<String>() {

            // Wird beim initialen Verboindungsaufbau aufgerufen
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

                // For each event received on the socket,
                in.onMessage(event -> {
                    out.write(event);
                });

                // When the socket is closed.
                in.onClose(() -> System.out.println("Disconnected"));
            }

        };
    }

    public static WebSocket<String> wsactor() {
        return WebSocket.withActor(MyWebSocketActor::props);
    }
}
