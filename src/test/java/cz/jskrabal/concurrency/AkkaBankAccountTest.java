package cz.jskrabal.concurrency;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import cz.jskrabal.concurrency.akka.BankAccountActor;
import cz.jskrabal.concurrency.akka.TransferActor;
import cz.jskrabal.concurrency.akka.message.*;
import cz.jskrabal.util.ActorUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class AkkaBankAccountTest extends MultiThreadTest{
    private ActorSystem system;

    @Before
    public void init() {
        super.init();
        system = ActorSystem.create();
    }

    @After
    public void cleanUp() {
        super.cleanUp();
        system.terminate();
    }

    @Test
    public void bankAccountActorTest() {
        new JavaTestKit(system) {{
            final ActorRef bankAccount = system.actorOf(Props.create(BankAccountActor.class));

            bankAccount.tell(new Deposit(10), getRef());
            expectMsgEquals(Done.instance());

            bankAccount.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(10));

            bankAccount.tell(new Withdraw(5), getRef());
            expectMsgEquals(Done.instance());

            bankAccount.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(5));

            bankAccount.tell(new Withdraw(10), getRef());
            expectMsgEquals(new Failed("Insufficient funds"));
        }};
    }

    @Test
    public void transferActorTest(){
        new JavaTestKit(system) {{
            final ActorRef bankAccount1 = system.actorOf(Props.create(BankAccountActor.class));
            final ActorRef bankAccount2 = system.actorOf(Props.create(BankAccountActor.class));
            final ActorRef transferActor = system.actorOf(Props.create(TransferActor.class));

            bankAccount1.tell(new Deposit(10), getRef());
            expectMsgEquals(Done.instance());

            transferActor.tell(new Transfer(5, bankAccount1, bankAccount2), getRef());
            expectMsgEquals(Done.instance());

            bankAccount1.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(5));

            bankAccount2.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(5));

        }};
    }

    @Test
    public void transferActorTest2(){
        new JavaTestKit(system) {{
            final ActorRef bankAccount1 = system.actorOf(Props.create(BankAccountActor.class));
            final ActorRef bankAccount2 = system.actorOf(Props.create(BankAccountActor.class));

            bankAccount1.tell(new Deposit(10000), getRef());
            expectMsgEquals(Done.instance());

            bankAccount2.tell(new Deposit(10000), getRef());
            expectMsgEquals(Done.instance());

            executorService.execute(() -> {
                transferTest(bankAccount1, bankAccount2);
                transferTest(bankAccount2, bankAccount1);
            });

            awaitCompletion();

            ActorUtils.busyWait();
            bankAccount1.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(10000));

            bankAccount2.tell(Balance.instance(), getRef());
            expectMsgEquals(new Balance.BalanceResponse(10000));

        }

            private void transferTest(ActorRef bankAccount1, ActorRef bankAccount2) {
                JavaTestKit probe = new JavaTestKit(system);
                ActorRef transferActor = system.actorOf(Props.create(TransferActor.class));
                transferActor.tell(new Transfer(1, bankAccount1, bankAccount2), probe.getRef());
                probe.expectMsgEquals(Done.instance());
            }
        };
    }
}
