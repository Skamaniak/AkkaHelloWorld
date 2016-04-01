package cz.jskrabal.concurrency.akka.message;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class Balance {
    private static final Balance INSTANCE = new Balance();

    private Balance() {
    }

    public static Balance instance(){
        return INSTANCE;
    }


    public static class BalanceResponse {
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
}
