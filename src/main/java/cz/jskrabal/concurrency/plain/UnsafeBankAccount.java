package cz.jskrabal.concurrency.plain;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class UnsafeBankAccount implements BankAccount {
    private int balance;

    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    @Override
    public void withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    @Override
    public long getCurrentBalance() {
        return balance;
    }

    /* Warning: Not Thread safe! */
    @Override
    public void transferTo(long amount, BankAccount to) {
        withdraw(amount);
        to.deposit(amount);
    }
}
