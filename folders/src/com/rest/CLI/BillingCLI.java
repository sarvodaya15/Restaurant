package com.rest.CLI;

import com.rest.models.Billing;
import com.rest.services.BillingService;
import java.util.List;

public class BillingCLI {
    private final BillingService billingService;

    public BillingCLI(BillingService billingService) {
        this.billingService = billingService;
    }

    public void printBill(Billing billing) {
        System.out.println("\n========== BILL ==========");
        System.out.println("Bill ID: " + billing.id());
        System.out.println("Order ID: " + billing.orderId());
        System.out.printf("%-20s %5s %10s %10s%n", "Item", "Qty", "Price", "Subtotal");

        for (Billing.LineItem item : billing.items()) {
            System.out.printf(
                    "%-20s %5d $%9.2f $%9.2f%n",
                    item.name(),
                    item.quantity(),
                    item.unitPrice(),
                    item.subtotal());
        }

        System.out.println("---------------------------");
        System.out.printf("%-37s $%9.2f%n", "Total:", billing.total());
        System.out.println("Payment method: " + billing.payment().method());
        System.out.println("Payment status: " + billing.payment().status());
        if (billing.payment().change() > 0) {
            System.out.printf("%-37s $%9.2f%n", "Change:", billing.payment().change());
        }
        System.out.println("===========================");
    }

    public void viewBills() {
        List<Billing> bills = billingService.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("No bills found. Complete an order payment first.");
            return;
        }

        bills.forEach(this::printBill);
    }
}
