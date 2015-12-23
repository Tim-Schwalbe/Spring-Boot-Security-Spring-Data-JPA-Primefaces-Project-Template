package de.tim.schwalbe.web.exception;

/**
 * @author tim.schwalbe
 */
public class NotAuthorizedException extends Exception {

        private String redirectPath;

        public NotAuthorizedException(String redirectPath) {
                this.redirectPath = redirectPath;
        }

        public NotAuthorizedException(String message, String redirectPath) {
                super(message);
                this.redirectPath = redirectPath;
        }

        public String getRedirectPath() {
                return this.redirectPath;
        }
}
