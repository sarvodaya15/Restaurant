package com.rest.CLI;

import com.rest.Payment.Cash;
import com.rest.Payment.Creditcard;
import com.rest.Payment.PaymentMethod;
import com.rest.Payment.Upi;
import com.rest.models.Billing;
import com.rest.services.BillingService;
import java.util.Scanner;

public class PaymentCLI {
    private final BillingService billingService;
    private final BillingCLI billingCLI;
    private final Scanner scanner;

    public PaymentCLI(
            BillingService billingService,
            BillingCLI billingCLI,
            Scanner scanner) {
        this.billingService = billingService;
        this.billingCLI = billingCLI;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("\n=== PAYMENT ===");
        System.out.print("Enter order ID: ");
        int orderId;
        try {
            orderId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Order ID must be a number.");
            return;
        }

        System.out.println("1. Cash");
        System.out.println("2. Credit card");
        System.out.println("3. UPI");
        System.out.print("Choose a payment method: ");
        PaymentMethod paymentMethod = switch (scanner.nextLine()) {
            case "1" -> new Cash();
            case "2" -> new Creditcard();
            case "3" -> new Upi();
            default -> null;
        };
        if (paymentMethod == null) {
            System.out.println("Invalid payment method.");
            return;
        }

        try {
            Billing billing = billingService.payAndCreateBill(orderId, paymentMethod);
            billingCLI.printBill(billing);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
