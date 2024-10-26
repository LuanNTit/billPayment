package org.exception;

public class BillNotFoundException extends Exception {
    private int id;

    public BillNotFoundException(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Sorry! Not found a bill with such id: " + id;
    }
}
