package controllers;

import akka.actor.*;
import json.JSONObject;
import play.api.libs.json.Json;
import play.libs.Akka;
import scala.util.parsing.json.JSONObject$;

import java.util.HashMap;

public class WebSocketActor extends UntypedActor {

    private final ActorRef out;

    public static Props props(ActorRef out) {
        return Props.create(WebSocketActor.class, out);
    }

    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String event = (String)message;

            JSONObject jsonNachricht = new JSONObject(event);

            String empfanger = jsonNachricht.get("empfaenger").toString();

            ActorPath targetpath= clientWebSocketActors.get(empfanger);
            ActorSelection targetActor = Akka.system().actorSelection(targetpath);
            System.out.println("Nachricht  an  "+ empfanger + "  gesendet");
            targetActor.tell(jsonNachricht.toString(), self());

        }

    }

    //Die Liste enthält die Pfade auf WebSocketActors zu den einzelnen User(Clients)
    static HashMap<String, ActorPath> clientWebSocketActors = new HashMap<>();

    /**
     * Die Methode schaltet sich vor die eigentliche props Methode und speichert
     * den User mit entsprechendem Pfad zwischen.
     */
    public static Props props(ActorRef out, String benutzerName) {
        clientWebSocketActors.put(benutzerName, out.path());
        return props(out);
    }
}
