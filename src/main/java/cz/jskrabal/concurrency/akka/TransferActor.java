package cz.jskrabal.concurrency.akka;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import cz.jskrabal.concurrency.akka.message.*;

import static cz.jskrabal.util.ActorUtils.inferType;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class TransferActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Transfer) {
            Transfer request = inferType(message);
            long amount = request.getAmount();

            request.getFrom().tell(new Withdraw(amount), getSelf());
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
            if(message instanceof Done) {
                to.tell(new Deposit(amount), getSelf());
                getContext().become(new AwaitDeposit<>(client));
            } else if(message instanceof Failed) {
                client.tell(message, getSelf());
                context().stop(getSelf());
            } else {
                unhandled(message);
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
            if(message instanceof Done) {
                client.tell(Done.instance(), getSelf());
                context().stop(getSelf());
            } else if(message instanceof Failed) {
                client.tell(message, getSelf());
                context().stop(getSelf());
            } else {
                unhandled(message);
            }
        }
    }
}
