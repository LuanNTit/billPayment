package org.example;

class Bill {
    int id;
    String type;
    int amount;
    String dueDate;
    String state;
    String provider;

    public Bill(int id, String type, int amount, String dueDate, String state, String provider) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }
}
