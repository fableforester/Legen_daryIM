package controllers;

import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import model.BenutzerDao;

public class PersistMessageActor extends UntypedActor {

    public static Props props = Props.create(PersistMessageActor.class);
    public BenutzerDao dao = new BenutzerDao();

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof JsonNode) {
            JsonNode msgJson = (JsonNode)msg;
            String sender = msgJson.get("sender").toString().replace("\"", "");
            if(sender.contains(":"))
                sender = sender.substring(sender.indexOf(":")+2);
            String empfaenger = msgJson.get("empfaenger").toString().replace("\"", "");
            String nachricht = msgJson.get("text").toString().replace("\"", "");
            if(!dao.persistMessage(sender, empfaenger, nachricht))
                System.out.println("Fehler beim persistieren der Nachricht");
        }
    }
}
