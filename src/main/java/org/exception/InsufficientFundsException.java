package org.exception;

public class InsufficientFundsException extends Exception {
    @Override
    public String getMessage() {
        return "Sorry! Not enough fund to proceed with payment.";
    }
}
