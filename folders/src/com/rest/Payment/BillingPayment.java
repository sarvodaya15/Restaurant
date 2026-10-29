package com.rest.Payment;

public record BillingPayment(String method, double amount, double change, String status) {
    public static BillingPayment from(PaymentReceipt receipt) {
        return new BillingPayment(
                receipt.method(),
                receipt.amount(),
                receipt.change(),
                receipt.status());
    }
}
