package com.rest.models;

import com.rest.Payment.BillingPayment;
import java.util.List;

public record Billing(
        int id,
        int orderId,
        List<LineItem> items,
        double total,
        BillingPayment payment) {

    public Billing {
        items = List.copyOf(items);
    }

    public record LineItem(String name, int quantity, double unitPrice, double subtotal) {
    }
}
