package cz.jskrabal.concurrency.akka.messages;

public class BalanceResponse {
    private long balance;

    public BalanceResponse(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceResponse that = (BalanceResponse) o;

        return balance == that.balance;

    }

    @Override
    public int hashCode() {
        return (int) (balance ^ (balance >>> 32));
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "balance=" + balance +
                '}';
    }
}