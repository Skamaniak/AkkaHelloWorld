package cz.jskrabal.tutorial.workload;

import akka.actor.UntypedActor;

import static cz.jskrabal.tutorial.workload.Messages.PiApproximation;
import static cz.jskrabal.util.ActorUtils.inferType;


public class ListenerActor extends UntypedActor {
    public void onReceive(Object message) {
        if (message instanceof PiApproximation) {
            PiApproximation approximation = inferType(message);
            System.out.println(String.format("\nPi approximation: %s\nCalculation time: %s ms",
                    approximation.getPi(), approximation.getDuration()));
            getContext().system().terminate();
        } else {
            unhandled(message);
        }
    }
}