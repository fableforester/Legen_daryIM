package controllers;

import akka.actor.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Akka;

import java.util.HashMap;

public class NachrichtenVersandActor extends UntypedActor {

    private final ActorRef out;

    ActorRef persistActor = Akka.system().actorOf(Props.create(PersistMessageActor.class));

    public static Props props(ActorRef out) {
        return Props.create(NachrichtenVersandActor.class, out);
    }

    public NachrichtenVersandActor(ActorRef out) {
        this.out = out;
    }

    public void onReceive(Object message) throws Exception {
        //TODO das senden der Nachricht in extra Actor
        //Das auslagern hat den Vorteil für scheduled jobs, regelmäßig senden an
        //offline kontakte

        if (message instanceof String) {
            String event = (String)message;

            //Json Objekt erzeugen
            JsonNode node = play.libs.Json.parse(event);

            //Empfänger ermitteln
            String empfanger = node.get("empfaenger").toString();

            //Ein anderer Actor persisitiert die Nachricht
            persistActor.tell(node, self());

            //Der Pfad zum Empfänger Actor wird rausgesucht
            ActorPath targetpath = clientWebSocketActors.get(empfanger);

            //Sollte der Empfänger offline sein geht die Nchricht ins leere
            if (targetpath == null)
                return;

            //Eine Referenz auf den Actor wird vom System abgerufen
            ActorSelection targetActor = Akka.system().actorSelection(targetpath);

            //Dem Actor die Nachricht senden
            targetActor.tell(node, self());

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
