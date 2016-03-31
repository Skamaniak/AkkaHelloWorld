package cz.jskrabal.concurrency.akka;

import akka.actor.UntypedActor;
import cz.jskrabal.concurrency.akka.message.*;

import static cz.jskrabal.util.ActorUtils.*;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class BankAccountActor extends UntypedActor {
    private long balance;

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof DepositRequest) {
            DepositRequest depositMessageMessage = inferType(message);
            balance += depositMessageMessage.getAmount();
            respond(DoneResponse.instance());
        } else if (message instanceof WithdrawRequest) {
            WithdrawRequest withdrawMessageMessage = inferType(message);
            long amount = withdrawMessageMessage.getAmount();
            if (balance >= amount) {
                balance -= amount;
                respond(DoneResponse.instance());
            } else {
                respond(new FailedResponse("Insufficient funds"));
            }
        } else if(message instanceof BalanceRequest) {
            respond(new BalanceResponse(balance));
        } else {
            unhandled(message);
        }
    }

    private void respond(Object response) {
        sender().tell(response, self());
    }

}
