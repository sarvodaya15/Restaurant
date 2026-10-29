package com.rest.CLI;

import com.rest.models.MenuItem;
import com.rest.models.OrderItem;
import com.rest.services.MenuItemService;
import com.rest.services.OrderItemService;
import java.util.List;
import java.util.Scanner;

public class OrderItemCLI {
    private final OrderItemService orderItemService;
    private final MenuItemService menuItemService;
    private final Scanner scanner;

    public OrderItemCLI(
            OrderItemService orderItemService,
            MenuItemService menuItemService,
            Scanner scanner) {
        this.orderItemService = orderItemService;
        this.menuItemService = menuItemService;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== ORDER ITEM MANAGEMENT ===");
            System.out.println("1. View all order items");
            System.out.println("2. Find order item by ID");
            System.out.println("3. Add order item");
            System.out.println("4. Update order item");
            System.out.println("5. Delete order item");
            System.out.println("0. Back");

            switch (readInt("Enter your choice: ")) {
                case 1 -> viewOrderItems();
                case 2 -> findOrderItem();
                case 3 -> createOrderItem();
                case 4 -> updateOrderItem();
                case 5 -> deleteOrderItem();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void createOrder() {
        List<MenuItem> menuItems = showMenuItems();
        if (menuItems.isEmpty()) {
            return;
        }

        int orderId = readInt("Enter order ID: ");
        if (orderId <= 0) {
            System.out.println("Order ID must be greater than zero.");
            return;
        }

        boolean addingItems = true;
        while (addingItems) {
            int menuItemId = chooseMenuItem(menuItems);
            int quantity = readInt("Enter quantity: ");
            try {
                orderItemService.createOrderItem(
                        orderItemService.getNextOrderItemId(),
                        orderId,
                        menuItemId,
                        quantity);
                System.out.println("Food added to order " + orderId + ".");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
            System.out.print("Add another food to this order? (y/n): ");
            addingItems = scanner.nextLine().trim().equalsIgnoreCase("y");
        }
    }

    public void viewOrders() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        if (orderItems.isEmpty()) {
            System.out.println("No orders found. Choose Create order to add one.");
            return;
        }

        orderItems.stream()
                .map(OrderItem::getOrderId)
                .distinct()
                .sorted()
                .forEach(orderId -> {
                    System.out.println("\n=== ORDER " + orderId + " ===");
                    orderItems.stream()
                            .filter(item -> item.getOrderId() == orderId)
                            .forEach(this::printOrderItem);
                    double total = orderItems.stream()
                            .filter(item -> item.getOrderId() == orderId)
                            .mapToDouble(OrderItem::getSubtotal)
                            .sum();
                    System.out.println("Total: $" + String.format("%.2f", total));
                });
    }

    private void findOrderItem() {
        int id = readInt("Enter order item ID: ");
        orderItemService.getOrderItem(id).ifPresentOrElse(
                this::printOrderItem,
                () -> System.out.println("Order item not found."));
    }

    private void viewOrderItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        System.out.println("\n=== AVAILABLE FOODS ===");
        if (menuItems.isEmpty()) {
            System.out.println("No foods are available. Add menu items first.");
        } else {
            menuItems.forEach(menuItem -> System.out.println(
                    menuItem.getId() + ". " + menuItem.getName()
                            + " - $" + String.format("%.2f", menuItem.getPrice())));
        }

        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        if (orderItems.isEmpty()) {
            System.out.println("\nNo order items have been added yet.");
            return;
        }

        System.out.println("\n=== ORDER ITEMS IN ORDERS ===");
        orderItems.forEach(this::printOrderItem);
    }

    private void printOrderItem(OrderItem orderItem) {
        String foodName = menuItemService.getMenuItem(orderItem.getMenuItemId())
                .map(MenuItem::getName)
                .orElse("Unknown menu item");
        System.out.println("OrderItem ID: " + orderItem.getId()
                + ", Order ID: " + orderItem.getOrderId()
                + ", Food: " + foodName
                + ", Quantity: " + orderItem.getQuantity()
                + ", Unit Price: " + orderItem.getUnitPrice()
                + ", Subtotal: " + orderItem.getSubtotal());
    }

    private void createOrderItem() {
        List<MenuItem> menuItems = showMenuItems();
        if (menuItems.isEmpty()) {
            return;
        }
        int id = readInt("Enter order item ID: ");
        int orderId = readInt("Enter order ID: ");
        int menuItemId = chooseMenuItem(menuItems);
        int quantity = readInt("Enter quantity: ");
        runAction(() -> orderItemService.createOrderItem(id, orderId, menuItemId, quantity));
    }

    private void updateOrderItem() {
        List<MenuItem> menuItems = showMenuItems();
        if (menuItems.isEmpty()) {
            return;
        }
        int id = readInt("Enter order item ID to update: ");
        int orderId = readInt("Enter new order ID: ");
        int menuItemId = chooseMenuItem(menuItems);
        int quantity = readInt("Enter new quantity: ");
        runAction(() -> orderItemService.updateOrderItem(id, orderId, menuItemId, quantity));
    }

    private List<MenuItem> showMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        if (menuItems.isEmpty()) {
            System.out.println("No menu items exist. Add a menu item first.");
            return menuItems;
        }

        System.out.println("\nChoose a food:");
        for (int index = 0; index < menuItems.size(); index++) {
            MenuItem menuItem = menuItems.get(index);
            System.out.println((index + 1) + ". " + menuItem.getName()
                    + " - $" + String.format("%.2f", menuItem.getPrice()));
        }
        return menuItems;
    }

    private int chooseMenuItem(List<MenuItem> menuItems) {
        while (true) {
            int choice = readInt("Enter food choice number: ");
            if (choice >= 1 && choice <= menuItems.size()) {
                return menuItems.get(choice - 1).getId();
            }
            System.out.println("Choose a number from 1 to " + menuItems.size() + ".");
        }
    }

    private void deleteOrderItem() {
        int id = readInt("Enter order item ID to delete: ");
        runAction(() -> orderItemService.deleteOrderItem(id));
    }

    private void runAction(Runnable action) {
        try {
            action.run();
            System.out.println("Operation successful.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }
}
