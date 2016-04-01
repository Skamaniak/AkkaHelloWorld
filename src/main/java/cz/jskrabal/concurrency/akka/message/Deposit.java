package cz.jskrabal.concurrency.akka.message;

public class Deposit {
        private long amount;
        public Deposit(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
            this.amount = amount;
        }

        public long getAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Deposit depositMessage = (Deposit) o;

            return amount == depositMessage.amount;

        }

        @Override
        public int hashCode() {
            return (int) (amount ^ (amount >>> 32));
        }
    }