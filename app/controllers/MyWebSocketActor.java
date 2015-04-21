package controllers;

import akka.actor.*;
import play.libs.Akka;

public class MyWebSocketActor extends UntypedActor {

    public static Props props(ActorRef out) {
        return Props.create(MyWebSocketActor.class, out);
    }

    private final ActorRef out;

    public MyWebSocketActor(ActorRef out) {
        this.out = out;
    }

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String event = (String)message;
            if (event.contains("TEST")){
                out.tell("MyWebSocketActor received your message: " + message, self());
                return;
            }

            if (event.contains("alex@web.de")){
                ActorPath targetpath= Application.list.get("alex@web.de");
                ActorSelection targetActor = Akka.system().actorSelection(targetpath);
                System.out.println("Nachricht an  Alex gesendet");
                targetActor.tell("TEST" + event, self());

            } else if (event.contains("moritz@web.de")){
                ActorPath targetpath= Application.list.get("moritz@web.de");
                ActorSelection targetActor = Akka.system().actorSelection(targetpath);
                System.out.println("Nachricht an  Moritz gesendet");
                targetActor.tell("TEST" + event, self());

            }

        }

    }
}
