package cz.jskrabal.concurrency.akka;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import cz.jskrabal.concurrency.akka.messages.*;

import static cz.jskrabal.util.ActorUtils.*;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class TransferActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof TransferRequest) {
            TransferRequest request = inferType(message);
            long amount = request.getAmount();

            request.getFrom().tell(new WithdrawRequest(amount), getSelf());
            getContext().become(new AwaitWithdraw<>(getSender(), request.getTo(), amount));
        }
    }

    private class AwaitWithdraw<T> implements Procedure<T> {
        private ActorRef client;
        private ActorRef to;
        private long amount;

        public AwaitWithdraw(ActorRef client, ActorRef to, long amount) {
            this.client = client;
            this.to = to;
            this.amount = amount;
        }

        @Override
        public void apply(T message) throws Exception {
            if(message instanceof DoneResponse) {
                to.tell(new DepositRequest(amount), getSelf());
                getContext().become(new AwaitDeposit<>(client));
            }
            if(message instanceof FailedResponse) {
                client.tell(message, getSelf());
                context().stop(getSelf());
            }
        }
    }

    private class AwaitDeposit<T> implements Procedure<T> {
        private ActorRef client;

        public AwaitDeposit(ActorRef client) {
            this.client = client;
        }

        @Override
        public void apply(T message) throws Exception {
            if(message instanceof DoneResponse) {
                client.tell(DoneResponse.instance(), getSelf());
                context().stop(getSelf());
            }
            if(message instanceof FailedResponse) {
                client.tell(message, getSelf());
                context().stop(getSelf());
            }
        }
    }
}
