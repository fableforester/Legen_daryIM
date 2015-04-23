package controllers;

import akka.actor.*;
import play.libs.Akka;

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

            if (event.contains("alex@web.de")){
                ActorPath targetpath= clientWebSocketActors.get("alex@web.de");
                ActorSelection targetActor = Akka.system().actorSelection(targetpath);
                System.out.println("Nachricht an  Alex gesendet");
                targetActor.tell(event, self());


            } else if (event.contains("moritz@web.de")){
                ActorPath targetpath= clientWebSocketActors.get("moritz@web.de");
                ActorSelection targetActor = Akka.system().actorSelection(targetpath);
                System.out.println("Nachricht an  Moritz gesendet");
                targetActor.tell(event, self());

            }

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
