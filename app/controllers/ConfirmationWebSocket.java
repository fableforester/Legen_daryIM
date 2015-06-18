package controllers;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Akka;

import java.util.HashMap;

public class ConfirmationWebSocket extends UntypedActor {

    private final ActorRef out;

    //Die Liste enthält die Pfade auf WebSocketActors zu den einzelnen User(Clients)
    static HashMap<String, ActorPath> clientWebSocketActors = new HashMap<>();

    public ConfirmationWebSocket(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out, String benutzerName) {
        clientWebSocketActors.put(benutzerName, out.path());
        return Props.create(ConfirmationWebSocket.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String event = (String)message;


            //Json Objekt erzeugen
            JsonNode node = play.libs.Json.parse(event);

            //Den sender ermitteln zu dem die confirmation geschickt wird
            String sender = node.get("sender").toString();
            sender = sender.substring(1, sender.length() - 1);

            //Der Pfad zum Empfänger Actor wird rausgesucht
            ActorPath targetpath = clientWebSocketActors.get(sender);

            //Sollte der Empfänger offline sein geht die Nchricht ins leere
            if (targetpath == null) {
                System.out.println("Targetpath ist null");
                return;
            }


            //Eine Referenz auf den Actor wird vom System abgerufen
            ActorRef targetActor = Akka.system().actorFor(targetpath);

            //Dem Actor die Nachricht senden
            targetActor.tell(node.toString(), self());

        }
    }
}
