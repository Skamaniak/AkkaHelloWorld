package cz.jskrabal.concurrency;

import cz.jskrabal.concurrency.plain.BankAccount;
import cz.jskrabal.concurrency.plain.SynchronizedBankAccount;
import cz.jskrabal.concurrency.plain.UnsafeBankAccount;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class PlainBankAccountTest extends MultiThreadTest{
    private static final int TESTS_COUNT = 10000;

    @Test
    public void unsafeBankAccountTest() throws InterruptedException {
        BankAccount bankAccount1 = new UnsafeBankAccount();
        BankAccount bankAccount2 = new UnsafeBankAccount();
        testTransfer(TESTS_COUNT, bankAccount1, bankAccount2);

        assertEquals(bankAccount1.getCurrentBalance(), bankAccount2.getCurrentBalance());
    }

    @Test
    public void synchronizedBankAccountTest() throws InterruptedException {
        BankAccount bankAccount1 = new SynchronizedBankAccount();
        BankAccount bankAccount2 = new SynchronizedBankAccount();

        testTransfer(TESTS_COUNT, bankAccount1, bankAccount2);
    }

    private void testTransfer(int count, BankAccount bankAccount1, BankAccount bankAccount2) throws InterruptedException {
        bankAccount1.deposit(count);
        bankAccount2.deposit(count);

        for (int i = 0; i < count; i++) {
            executorService.execute(() -> bankAccount1.transferTo(1, bankAccount2));
            executorService.execute(() -> bankAccount2.transferTo(1, bankAccount1));
        }
        Thread.sleep(500);
        detectDeadlocks();
        awaitCompletion();
    }


    private void detectDeadlocks(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads();

        if (threadIds != null) {
            ThreadInfo[] infos = bean.getThreadInfo(threadIds);

            for (ThreadInfo info : infos) {
                System.out.println("Deadlock detected: " + info.getThreadName());
            }
        }
    }
}
