package cz.jskrabal.tutorial;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import cz.jskrabal.util.ActorUtils;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class ToggleActor extends UntypedActor {
    private static final String TOGGLE_MESSAGE = "toggle";

    @Override
    public void onReceive(Object message) throws Exception {
        if(message.equals(TOGGLE_MESSAGE)) {
            getSender().tell("Switched on", getSelf());
            getContext().become(switchedOn);
        } else {
            unhandled(message);
        }
    }

    private Procedure<Object> switchedOn = message -> {
        if(message.equals(TOGGLE_MESSAGE)) {
            getSender().tell("Switched off", getSelf());
            getContext().unbecome();
        } else {
            unhandled(message);
        }
    };


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();

        ActorRef toggleActor = system.actorOf(Props.create(ToggleActor.class));
        ActorRef printActor = system.actorOf(Props.create(ActorUtils.PrintActor.class));

        for (int i = 0; i < 10; i++) {
            toggleActor.tell(TOGGLE_MESSAGE, printActor);
        }

        ActorUtils.busyWait();
        system.shutdown();
    }
}
