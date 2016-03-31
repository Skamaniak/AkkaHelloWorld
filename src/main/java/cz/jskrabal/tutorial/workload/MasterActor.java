package cz.jskrabal.tutorial.workload;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

import static cz.jskrabal.tutorial.workload.Messages.*;
import static cz.jskrabal.util.ActorUtils.*;

public class MasterActor extends UntypedActor {
    private final int nrOfMessages;
    private final int nrOfElements;

    private double pi;
    private int nrOfResults;
    private final long start = System.currentTimeMillis();

    private final ActorRef listener;
    private final ActorRef workerRouter;

    MasterActor(final int nrOfWorkers, int nrOfMessages, int nrOfElements, ActorRef listener) {
        this.nrOfMessages = nrOfMessages;
        this.nrOfElements = nrOfElements;
        this.listener = listener;

        workerRouter = getContext()
                        .actorOf(Props.create(WorkerActor.class)
                        .withRouter(new RoundRobinPool(nrOfWorkers)), "workerRouter");
    }

    public void onReceive(Object message) {
        if (message instanceof Messages.Calculate) {
            for (int start = 0; start < nrOfMessages; start++) {
                workerRouter.tell(new Work(start, nrOfElements), getSelf());
            }
        } else if (message instanceof Result) {
            Result result = inferType(message);
            pi += result.getValue();
            nrOfResults += 1;
            if (nrOfResults == nrOfMessages) {
                long duration = System.currentTimeMillis() - start;
                listener.tell(new PiApproximation(pi, duration), getSelf());

                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    private static void calculate(final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) {
        ActorSystem system = ActorSystem.create("PiSystem");

        final ActorRef listener = system.actorOf(Props.create(ListenerActor.class), "listener");
        ActorRef master = system.actorOf(Props.create(MasterActor.class, nrOfWorkers, nrOfMessages, nrOfElements, listener), "master");
        master.tell(new Calculate(), system.guardian());

    }

    public static void main(String[] args) {
        calculate(4, 10000, 10000);
    }
}