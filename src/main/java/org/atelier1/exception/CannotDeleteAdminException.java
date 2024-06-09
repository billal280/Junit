package org.atelier1.exception;

public class CannotDeleteAdminException extends RuntimeException {
    public CannotDeleteAdminException(String message) {
        super(message);
    }
}