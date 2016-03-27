package cz.jskrabal.plain;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class SynchronizedBankAccount implements BankAccount {
    private long balance;

    @Override
    public synchronized void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    @Override
    public synchronized void withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    @Override
    public synchronized long getCurrentBalance() {
        return balance;
    }

    @Override
    public synchronized void transferTo(long amount, BankAccount to) {
        /* Warning: May cause deadlock! */
        synchronized (to) {
            withdraw(amount);
            to.deposit(amount);
        }
    }
}
