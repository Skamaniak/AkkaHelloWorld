package cz.jskrabal.akka.messages;

public class FailedResponse {
        private String errorMessage;

        public FailedResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FailedResponse failedMessage = (FailedResponse) o;

            return errorMessage != null ? errorMessage.equals(failedMessage.errorMessage) : failedMessage.errorMessage == null;

        }

        @Override
        public int hashCode() {
            return errorMessage != null ? errorMessage.hashCode() : 0;
        }
    }