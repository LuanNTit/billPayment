package org.exception;

public class BillAlreadyPaidException extends Exception {
    @Override
    public String getMessage() {
        return "Bill is already paid.";
    }
}
