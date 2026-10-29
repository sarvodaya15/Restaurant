package com.rest.Payment;

public class Cash implements PaymentMethod {
    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt("Cash", amount, 0, "Payment successful");
    }

    @Override
    public String getMethodName() {
        return "Cash";
    }
}
