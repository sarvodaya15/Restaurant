package com.rest.Payment;

public class Upi implements PaymentMethod {
    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt(
                "UPI", amount, 0,
                "Simulated payment successful (no real UPI payment was sent)");
    }

    @Override
    public String getMethodName() {
        return "UPI";
    }
}
