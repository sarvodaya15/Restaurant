package com.rest.models;

public class OrderItem {
    private final int id;
    private final int orderId;
    private final int menuItemId;
    private final int quantity;
    private final double unitPrice;

    public OrderItem(int id, int orderId, int menuItemId, int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubtotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem ID: " + id
                + ", Order ID: " + orderId
                + ", Menu Item ID: " + menuItemId
                + ", Quantity: " + quantity
                + ", Unit Price: " + unitPrice
                + ", Subtotal: " + getSubtotal();
    }
}
