package org.exception;

public class PaymentAlreadyScheduleException extends Exception{
    @Override
    public String getMessage() {
        return "Sorry! Payment has already been scheduled for this bill.";
    }
}
