package com.rest.services;

import com.rest.models.MenuItem;
import com.rest.models.OrderItem;
import com.rest.repository.MenuItemRepository;
import com.rest.repository.OrderItemRepository;
import java.util.List;
import java.util.Optional;

public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderItemService(
            OrderItemRepository orderItemRepository,
            MenuItemRepository menuItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItem(int id) {
        return orderItemRepository.findById(id);
    }

    public int getNextOrderItemId() {
        return orderItemRepository.findAll().stream()
                .mapToInt(OrderItem::getId)
                .max()
                .orElse(0) + 1;
    }

    public void createOrderItem(int id, int orderId, int menuItemId, int quantity) {
        validateOrderItem(id, orderId, quantity);
        if (orderItemRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("An order item with that ID already exists.");
        }
        MenuItem menuItem = findMenuItem(menuItemId);
        orderItemRepository.add(
                new OrderItem(id, orderId, menuItemId, quantity, menuItem.getPrice()));
    }

    public void updateOrderItem(int id, int orderId, int menuItemId, int quantity) {
        validateOrderItem(id, orderId, quantity);
        MenuItem menuItem = findMenuItem(menuItemId);
        OrderItem updatedOrderItem =
                new OrderItem(id, orderId, menuItemId, quantity, menuItem.getPrice());
        if (!orderItemRepository.update(updatedOrderItem)) {
            throw new IllegalArgumentException("Order item not found.");
        }
    }

    public void deleteOrderItem(int id) {
        if (!orderItemRepository.deleteById(id)) {
            throw new IllegalArgumentException("Order item not found.");
        }
    }

    private MenuItem findMenuItem(int menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found."));
    }

    private void validateOrderItem(int id, int orderId, int quantity) {
        if (id <= 0 || orderId <= 0) {
            throw new IllegalArgumentException("IDs must be greater than zero.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
