package com.rest.Payment;

public record PaymentReceipt(String method, double amount, double change, String status) {
    @Override
    public String toString() {
        String receipt = "Payment method: " + method
                + ", Amount: $" + String.format("%.2f", amount)
                + ", Status: " + status;
        if (change > 0) {
            receipt += ", Change: $" + String.format("%.2f", change);
        }
        return receipt;
    }
}
