package controllers;

import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;

public class PersistMessageActor extends UntypedActor {

    public static Props props = Props.create(PersistMessageActor.class);

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof JsonNode) {
            JsonNode msgJson = (JsonNode)msg;
            //TODO Nachricht in die Datenbank schreiben
        }
    }
}
