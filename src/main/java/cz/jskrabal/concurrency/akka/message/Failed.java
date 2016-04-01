package cz.jskrabal.concurrency.akka.message;

public class Failed {
        private String errorMessage;

        public Failed(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Failed failedMessage = (Failed) o;

            return errorMessage != null ? errorMessage.equals(failedMessage.errorMessage) : failedMessage.errorMessage == null;

        }

        @Override
        public int hashCode() {
            return errorMessage != null ? errorMessage.hashCode() : 0;
        }
    }