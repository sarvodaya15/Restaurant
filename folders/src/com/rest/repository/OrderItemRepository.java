package com.rest.repository;

import com.rest.models.OrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemRepository {
    private final List<OrderItem> orderItems = new ArrayList<>();

    public List<OrderItem> findAll() {
        return new ArrayList<>(orderItems);
    }

    public Optional<OrderItem> findById(int id) {
        return orderItems.stream()
                .filter(orderItem -> orderItem.getId() == id)
                .findFirst();
    }

    public void add(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public boolean update(OrderItem updatedOrderItem) {
        for (int index = 0; index < orderItems.size(); index++) {
            if (orderItems.get(index).getId() == updatedOrderItem.getId()) {
                orderItems.set(index, updatedOrderItem);
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(int id) {
        return orderItems.removeIf(orderItem -> orderItem.getId() == id);
    }

    public int deleteByOrderId(int orderId) {
        int initialSize = orderItems.size();
        orderItems.removeIf(orderItem -> orderItem.getOrderId() == orderId);
        return initialSize - orderItems.size();
    }
}
