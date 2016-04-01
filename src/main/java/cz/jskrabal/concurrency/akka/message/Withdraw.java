package cz.jskrabal.concurrency.akka.message;

public class Withdraw {
        private long amount;

        public Withdraw(long amount) {
            this.amount = amount;
        }

        public long getAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Withdraw withdrawMessage = (Withdraw) o;

            return amount == withdrawMessage.amount;

        }

        @Override
        public int hashCode() {
            return (int) (amount ^ (amount >>> 32));
        }
    }