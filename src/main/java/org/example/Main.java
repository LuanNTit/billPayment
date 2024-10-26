package org.example;

import org.exception.BillAlreadyPaidException;
import org.exception.BillNotFoundException;
import org.exception.InsufficientFundsException;
import org.exception.PaymentAlreadyScheduleException;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BillManager billManager = new BillManager();

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] command = input.split(" ");

            switch (command[0]) {
                case "CASH_IN":
                    int amount = Integer.parseInt(command[1]);
                    billManager.cashIn(amount);
                    break;
                case "LIST_BILL":
                    billManager.listBills();
                    break;
                case "PAY":
                    int[] billIds = getBillsToCommands(command);
                    try {
                        billManager.pay(billIds);
                    } catch (BillNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (BillAlreadyPaidException e) {
                        System.out.println(e.getMessage());
                    } catch (InsufficientFundsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "DUE_DATE":
                    billManager.listDueBills();
                    break;
                case "SCHEDULE":
                    int scheduleBillId = Integer.parseInt(command[1]);
                    String date = command[2];
                    try {
                        billManager.schedule(scheduleBillId, date);
                    } catch (BillNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (PaymentAlreadyScheduleException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LIST_PAYMENT":
                    billManager.listPayments();
                    break;
                case "SEARCH_BILL_BY_PROVIDER":
                    String provider = command[1];
                    billManager.searchBillByProvider(provider);
                    break;
                case "EXIT":
                    System.out.println("Good bye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
    public static int[] getBillsToCommands(String[] command) {
        int[] billIds = new int[command.length - 1];
        for (int i = 1; i < command.length; i++) {
            billIds[i - 1] = Integer.parseInt(command[i]);
        }
        return billIds;
    }
}