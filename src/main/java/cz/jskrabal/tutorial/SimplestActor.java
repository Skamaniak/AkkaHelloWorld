package cz.jskrabal.tutorial;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import cz.jskrabal.util.ActorUtils;

public class SimplestActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            if ("shutdown".equals(message)) {
                System.out.println("Shutting down...");
                getContext().stop(getSelf());
            } else {
                System.out.println("Received message: " + message);
            }
        }
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();

        Props props = Props.create(SimplestActor.class);
        ActorRef simplestActorRef = actorSystem.actorOf(props, "SimplestActor");

        simplestActorRef.tell("Message 1", ActorRef.noSender());
        simplestActorRef.tell("Message 2", ActorRef.noSender());
        simplestActorRef.tell("shutdown", ActorRef.noSender());
        simplestActorRef.tell("Message 3", ActorRef.noSender());

        ActorUtils.busyWait();
        actorSystem.shutdown();
    }
}
