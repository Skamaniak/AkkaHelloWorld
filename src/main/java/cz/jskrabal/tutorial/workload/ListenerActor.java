package cz.jskrabal.tutorial.workload;

import akka.actor.UntypedActor;

import static cz.jskrabal.tutorial.workload.Messages.*;
import static cz.jskrabal.util.ActorUtils.*;


public class ListenerActor extends UntypedActor {
    public void onReceive(Object message) {
        if (message instanceof PiApproximation) {
            PiApproximation approximation = inferType(message);
            System.out.println(String.format("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s",
                    approximation.getPi(), approximation.getDuration()));
            getContext().system().shutdown();
        } else {
            unhandled(message);
        }
    }
}