package org.example;

class Payment {
    int id;
    int amount;
    String paymentDate;
    String state;
    int billId;

    public Payment(int id, int amount, String paymentDate, String state, int billId) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }
}
