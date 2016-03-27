package cz.jskrabal.tutorial;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import cz.jskrabal.util.ActorUtils;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class GreetingsActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof String) {
            getSender().tell("Hello " + message + '!', getSelf());
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();

        ActorRef greetingsActor = system.actorOf(Props.create(GreetingsActor.class));
        ActorRef printActor = system.actorOf(Props.create(ActorUtils.PrintActor.class));

        greetingsActor.tell("John Doe", printActor);

        ActorUtils.busyWait();
        system.shutdown();
    }

}
