package com.rest.Payment;

public interface PaymentMethod {

    PaymentReceipt pay(double amount);

    String getMethodName();

}
