package cz.jskrabal.concurrency.akka.message;

public class WithdrawRequest {
        private long amount;

        public WithdrawRequest(long amount) {
            this.amount = amount;
        }

        public long getAmount() {
            return amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WithdrawRequest withdrawMessage = (WithdrawRequest) o;

            return amount == withdrawMessage.amount;

        }

        @Override
        public int hashCode() {
            return (int) (amount ^ (amount >>> 32));
        }
    }