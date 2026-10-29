package com.rest.services;

import com.rest.Payment.PaymentMethod;
import com.rest.Payment.PaymentReceipt;
import com.rest.models.OrderItem;
import com.rest.models.Payment;
import com.rest.repository.OrderItemRepository;
import com.rest.repository.PaymentRepository;
import java.util.List;

public class PaymentService {
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository) {
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        return orderItemRepository.findAll().stream()
                .filter(orderItem -> orderItem.getOrderId() == orderId)
                .toList();
    }

    public double getOrderTotal(int orderId) {
        return getOrderItems(orderId).stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public PaymentReceipt pay(int orderId, PaymentMethod paymentMethod) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than zero.");
        }
        double total = getOrderTotal(orderId);
        if (total <= 0) {
            throw new IllegalArgumentException("No order items found for that order ID.");
        }
        PaymentReceipt receipt = paymentMethod.pay(total);
        save(receipt);
        return receipt;
    }

    public PaymentReceipt payAndComplete(int orderId, PaymentMethod paymentMethod) {
        PaymentReceipt receipt = pay(orderId, paymentMethod);
        orderItemRepository.deleteByOrderId(orderId);
        return receipt;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    private void save(PaymentReceipt receipt) {
        int nextPaymentId = paymentRepository.findAll().stream()
                .mapToInt(Payment::getId)
                .max()
                .orElse(0) + 1;
        paymentRepository.add(new Payment(
                nextPaymentId,
                receipt.amount(),
                receipt.method(),
                receipt.status()));
    }
}
