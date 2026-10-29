package com.rest.Payment;

public class Creditcard implements PaymentMethod {
    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt(
                "Credit card", amount, 0, "Simulated only - no card was charged");
    }

    @Override
    public String getMethodName() {
        return "Credit card";
    }
}
