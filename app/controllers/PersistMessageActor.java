package controllers;

import akka.actor.Props;
import akka.actor.UntypedActor;
import json.JSONObject;

public class PersistMessageActor extends UntypedActor {

    public static Props props = Props.create(PersistMessageActor.class);

    public void onReceive(Object msg) throws Exception {
        if (msg instanceof JSONObject) {
            JSONObject msgJson = (JSONObject)msg;
            //TODO Nachricht in die Datenbank schreiben
        }
    }
}
