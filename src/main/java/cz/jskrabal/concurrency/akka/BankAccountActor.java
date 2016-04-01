package cz.jskrabal.concurrency.akka;

import akka.actor.UntypedActor;
import cz.jskrabal.concurrency.akka.message.*;

import static cz.jskrabal.util.ActorUtils.inferType;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class BankAccountActor extends UntypedActor {
    private long balance;

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Deposit) {
            Deposit depositMessageMessage = inferType(message);
            balance += depositMessageMessage.getAmount();
            respond(Done.instance());
        } else if (message instanceof Withdraw) {
            Withdraw withdrawMessageMessage = inferType(message);
            long amount = withdrawMessageMessage.getAmount();
            if (balance >= amount) {
                balance -= amount;
                respond(Done.instance());
            } else {
                respond(new Failed("Insufficient funds"));
            }
        } else if(message instanceof Balance) {
            respond(new Balance.BalanceResponse(balance));
        } else {
            unhandled(message);
        }
    }

    private void respond(Object response) {
        sender().tell(response, self());
    }

}
