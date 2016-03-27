package cz.jskrabal.concurrency.plain;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public interface BankAccount {

    /**
     * Deposits given amount to bank account
     * @param amount to deposit
     */
    void deposit(long amount);

    /**
     * Withdraws given amount from bank account
     * @param amount to withdraw
     */
    void withdraw(long amount);

    /**
     * Returns current bank account balance
     * @return balance
     */
    long getCurrentBalance();

    /**
     * Transfers given amount to given bank account
     * @param amount to transfer
     * @param to target bank account
     */
    void transferTo(long amount, BankAccount to);
}
