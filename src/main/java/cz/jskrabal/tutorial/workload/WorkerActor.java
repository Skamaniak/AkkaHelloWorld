package cz.jskrabal.tutorial.workload;
/* =========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2016 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * ========================================================================= */

import akka.actor.UntypedActor;

import static cz.jskrabal.tutorial.workload.Messages.Result;
import static cz.jskrabal.tutorial.workload.Messages.Work;
import static cz.jskrabal.util.ActorUtils.*;

/**
 * @author Jan Škrabal (jan.skrabal@ibacz.eu)
 */
public class WorkerActor extends UntypedActor {

    private double calculatePiFor(int start, int nrOfElements) {
        double acc = 0.0;
        for (int i = start * nrOfElements; i <= ((start + 1) * nrOfElements - 1); i++) {
            acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
        }
        return acc;
    }

    public void onReceive(Object message) {
        if (message instanceof Work) {

            Work work = inferType(message);
            double result = calculatePiFor(work.getStart(), work.getNrOfElements());
            getSender().tell(new Result(result), getSelf());
        } else {
            unhandled(message);
        }
    }
}
