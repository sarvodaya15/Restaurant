package com.rest.services;

import com.rest.Payment.BillingPayment;
import com.rest.Payment.PaymentMethod;
import com.rest.Payment.PaymentReceipt;
import com.rest.models.Billing;
import com.rest.models.MenuItem;
import com.rest.models.OrderItem;
import com.rest.repository.BillingRepository;
import com.rest.repository.MenuItemRepository;
import java.util.List;

public class BillingService {
    private final PaymentService paymentService;
    private final MenuItemRepository menuItemRepository;
    private final BillingRepository billingRepository;

    public BillingService(
            PaymentService paymentService,
            MenuItemRepository menuItemRepository,
            BillingRepository billingRepository) {
        this.paymentService = paymentService;
        this.menuItemRepository = menuItemRepository;
        this.billingRepository = billingRepository;
    }

    public Billing payAndCreateBill(int orderId, PaymentMethod paymentMethod) {
        List<OrderItem> orderItems = paymentService.getOrderItems(orderId);
        PaymentReceipt receipt = paymentService.payAndComplete(orderId, paymentMethod);
        List<Billing.LineItem> billItems = orderItems.stream()
                .map(orderItem -> new Billing.LineItem(
                        menuItemRepository.findById(orderItem.getMenuItemId())
                                .map(MenuItem::getName)
                                .orElse("Item " + orderItem.getMenuItemId()),
                        orderItem.getQuantity(),
                        orderItem.getUnitPrice(),
                        orderItem.getSubtotal()))
                .toList();

        int nextId = billingRepository.findAll().stream()
                .mapToInt(Billing::id)
                .max()
                .orElse(0) + 1;
        Billing billing = new Billing(
                nextId,
                orderId,
                billItems,
                receipt.amount(),
                BillingPayment.from(receipt));
        billingRepository.add(billing);
        return billing;
    }

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillById(int id) {
        return billingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found."));
    }
}
