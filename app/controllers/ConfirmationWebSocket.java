package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by alexanderberg on 16.06.15.
 */
public class ConfirmationWebSocket extends UntypedActor {

    private final ActorRef out;

    public ConfirmationWebSocket(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(ConfirmationWebSocket.class, out);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            System.out.println("tada!");
        }
    }
}
