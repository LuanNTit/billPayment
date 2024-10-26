package org.example;

import org.exception.BillAlreadyPaidException;
import org.exception.BillNotFoundException;
import org.exception.InsufficientFundsException;
import org.exception.PaymentAlreadyScheduleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BillManagerTest {
    BillManager billManager = null;
    @BeforeEach
    void setUp() {
        billManager = new BillManager();
        billManager.cashIn(1000000);
    }

    @Test
    void cashIn() {
        billManager.cashIn(1000000);
        assertEquals(1000000, billManager.getBalance());
    }

    @Test
    void listBills() {
        billManager.listBills();
        assertEquals(3,billManager.getBills().size());
    }

    @Test
    void payWithoutException() {
        boolean thown = false;
        int expected = 0;
        try {
            expected = billManager.getBalance() - billManager.getBillById(1).amount;
            billManager.pay(1);
            assertEquals(expected, billManager.getBalance());
            assertEquals("PAID", billManager.getBillById(1).state);
        } catch (BillAlreadyPaidException e) {
            System.out.println(e.getMessage());
            thown = true;
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
            thown = true;
        } catch (BillNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(false, thown);
    }

    @Test
    void payWithBillNotFoundException() {
        boolean thrown = false;
        int expected = 0;
        try {
            expected = billManager.getBalance();
            billManager.pay(10);
            assertEquals(expected, billManager.getBalance());
//            assertEquals("NOT_PAID", billManager.getBillById(10).state);
        } catch (BillNotFoundException e) {
            System.out.println(e.getMessage());
            thrown = true;
        } catch (BillAlreadyPaidException e) {
            System.out.println(e.getMessage());
            thrown = false;
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
            thrown = false;
        }
        assertEquals(true, thrown);
    }

    @Test
    void listDueBills() {
        payWithoutException();
        billManager.listDueBills();
        assertEquals(2, billManager.getBillByDuedate().size());
    }

    @Test
    void scheduleWithoutException() {
        boolean thrown = false;
        try {
            billManager.schedule(2, "28/10/2020");
            assertEquals("PENDING", billManager.getPayment(2, "28/10/2020").state);
        } catch (BillNotFoundException e) {
            System.out.println(e.getMessage());
            thrown = true;
        } catch (PaymentAlreadyScheduleException e) {
            System.out.println(e.getMessage());
            thrown = true;
        }
        assertEquals(false, thrown);
    }
    @Test
    void scheduleWithPaymentAlreadyScheduleException() {
        boolean thrown = false;
        try {
            billManager.schedule(2, "28/10/2020");
            billManager.schedule(2, "28/10/2020");
        } catch (BillNotFoundException e) {
            System.out.println(e.getMessage());
            thrown = false;
        } catch (PaymentAlreadyScheduleException e) {
            System.out.println(e.getMessage());
            thrown = true;
        }
        assertEquals(true, thrown);
    }

    @Test
    void listPayments() {
        payWithoutException();
        scheduleWithoutException();
        billManager.listPayments();
        assertEquals(2, billManager.getPayments().size());
    }

    @Test
    void searchBillByProvider() {
        billManager.searchBillByProvider("VNPT");
        assertEquals(1, billManager.getBillByProvider("VNPT").size());
    }
}