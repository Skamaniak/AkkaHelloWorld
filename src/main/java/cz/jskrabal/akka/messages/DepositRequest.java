package cz.jskrabal.akka.messages;

public class DepositRequest {
        private long amount;
        public DepositRequest(long amount) {
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

            DepositRequest depositMessage = (DepositRequest) o;

            return amount == depositMessage.amount;

        }

        @Override
        public int hashCode() {
            return (int) (amount ^ (amount >>> 32));
        }
    }