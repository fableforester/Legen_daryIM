package controllers;

import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import model.BenutzerDao;

import java.util.Date;

public class PersistMessageActor extends UntypedActor {

    public static Props props = Props.create(PersistMessageActor.class);
    public BenutzerDao dao = BenutzerDao.getInstance();

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof JsonNode) {
            String timestamp = (new Date()).toString();
            JsonNode msgJson = (JsonNode)msg;
            String sender = msgJson.get("sender").toString().replace("\"", "");
            String empfaenger = msgJson.get("empfaenger").toString().replace("\"", "");
            String nachricht = msgJson.get("text").toString().replace("\"", "");
            if(!dao.persistMessage(sender, empfaenger, nachricht, timestamp))
                System.out.println("Fehler beim persistieren der Nachricht");
        }
    }
}
