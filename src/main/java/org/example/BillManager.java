package org.example;

import org.exception.*;

import java.util.ArrayList;
import java.util.List;

class BillManager {
    private List<Bill> bills = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private int balance = 0;
    private int nextBillId = 1;
    private int nextPaymentId = 1;

    public BillManager() {
        // Sample data
        bills.add(new Bill(nextBillId++, "ELECTRIC", 200000, "25/10/2020", "NOT_PAID", "EVN HCMC"));
        bills.add(new Bill(nextBillId++, "WATER", 175000, "30/10/2020", "NOT_PAID", "SAVACO HCMC"));
        bills.add(new Bill(nextBillId++, "INTERNET", 800000, "30/11/2020", "NOT_PAID", "VNPT"));
    }

    public int getBalance() {
        return balance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void cashIn(int amount) {
        balance += amount;
        System.out.println("Your available balance: " + balance);
    }

    public void listBills() {
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : bills) {
            System.out.printf("%d. %s %d %s %s %s%n", bill.id, bill.type, bill.amount, bill.dueDate, bill.state, bill.provider);
        }
    }

    public void pay(int... billIds) throws BillAlreadyPaidException, InsufficientFundsException, BillNotFoundException {
        if (billIds.length == 1) {
            pay(billIds[0]);
        } else {
            int totalAmount = 0;
            for (int id = 0; id < billIds.length; id++) {
                Bill bill = getBillById(billIds[id]);
                totalAmount += bill.amount;
            }
            if (balance < totalAmount) {
                throw new InsufficientFundsException();
            }
            for (int id = 0; id < billIds.length; id++) {
                pay(billIds[id]);
            }
        }
    }

    public void pay(int billId) throws BillAlreadyPaidException, InsufficientFundsException, BillNotFoundException {
        Bill bill = getBillById(billId);
        if (bill.state.equals("PAID")) {
            throw new BillAlreadyPaidException();
        }
        if (balance < bill.amount) {
            throw new InsufficientFundsException();
        }
        balance -= bill.amount;
        bill.state = "PAID";
        payments.add(new Payment(nextPaymentId++, bill.amount, bill.dueDate, "PROCESSED", bill.id));
        System.out.println("Payment has been completed for Bill with id " + bill.id + ".");
        System.out.println("Your current balance is: " + balance);
    }

    public void listDueBills() {
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : bills) {
            if (bill.state.equals("NOT_PAID")) {
                System.out.printf("%d. %s %d %s %s %s%n", bill.id, bill.type, bill.amount, bill.dueDate, bill.state, bill.provider);
            }
        }
    }

    public void schedule(int billId, String date) throws BillNotFoundException, PaymentAlreadyScheduleException {
        Bill bill = getBillById(billId);
        if (getPayment(billId, date) != null) {
            throw new PaymentAlreadyScheduleException();
        }
        getPayment(billId, date);
        payments.add(new Payment(nextPaymentId++, bill.amount, date, "PENDING", bill.id));
        System.out.println("Payment for bill id " + bill.id + " is scheduled on " + date);
    }

    public void listPayments() {
        System.out.println("No. Amount Payment Date State Bill Id");
        for (Payment payment : payments) {
            System.out.printf("%d. %d %s %s %d%n", payment.id, payment.amount, payment.paymentDate, payment.state, payment.billId);
        }
    }

    public void searchBillByProvider(String provider) {
        System.out.println("Bill No. Type Amount Due Date State PROVIDER");
        for (Bill bill : bills) {
            if (bill.provider.equalsIgnoreCase(provider)) {
                System.out.printf("%d. %s %d %s %s %s%n", bill.id, bill.type, bill.amount, bill.dueDate, bill.state, bill.provider);
            }
        }
    }

    public Bill getBillById(int billId) throws BillNotFoundException {
        for (Bill bill : bills) {
            if (bill.id == billId) {
                return bill;
            }
        }
        throw new BillNotFoundException(billId);
    }

    public ArrayList<Bill> getBillByDuedate() {
        ArrayList<Bill> dueDate = new ArrayList<>();
        for (Bill bill: bills) {
            if (bill.state.equals("NOT_PAID")) {
                dueDate.add(bill);
            }
        }
        return dueDate;
    }
    public Payment getPayment(int billId, String date) {
        for (Payment pay: payments) {
            if ((pay.billId == billId) && pay.paymentDate.equals(date)) {
                return pay;
            }
        }
        return null;
    }
    public ArrayList<Bill> getBillByProvider(String provider) {
        ArrayList<Bill> billProvider = new ArrayList<>();
        for (Bill bill: bills) {
            if (bill.provider.equalsIgnoreCase(provider)) {
                billProvider.add(bill);
            }
        }
        return billProvider;
    }
}
