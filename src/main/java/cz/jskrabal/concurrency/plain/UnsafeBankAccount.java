package cz.jskrabal.concurrency.plain;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class UnsafeBankAccount implements BankAccount {
    private long balance;

    //Warning: Not thread safe! (modification of long is not atomic)
    @Override
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    //Warning: Not thread safe! (more than one thread can pass the condition at a time)
    @Override
    public void withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    //Warning: Not thread safe! (Value can be accessed during modification and method can return invalid number)
    @Override
    public long getCurrentBalance() {
        return balance;
    }

    //Warning: Not Thread safe! (Obvious reasons :-))
    @Override
    public void transferTo(long amount, BankAccount to) {
        withdraw(amount);
        to.deposit(amount);
    }
}
