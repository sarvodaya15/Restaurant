
package com.rest.services;

import com.rest.CLI.BillingCLI;
import com.rest.CLI.OrderItemCLI;
import com.rest.CLI.PaymentCLI;
import com.rest.models.MenuItem;
import com.rest.models.Tables;
import com.rest.repository.BillingRepository;
import com.rest.repository.MenuItemRepository;
import com.rest.repository.OrderItemRepository;
import com.rest.repository.PaymentRepository;
import com.rest.repository.TableRepository;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuItemRepository menuItemRepository = new MenuItemRepository();
        MenuItemService menuItemService = new MenuItemService(menuItemRepository);
        OrderItemRepository orderItemRepository = new OrderItemRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        BillingRepository billingRepository = new BillingRepository();
        BillingService billingService = new BillingService(
                new PaymentService(orderItemRepository, paymentRepository),
                menuItemRepository,
                billingRepository);
        BillingCLI billingCLI = new BillingCLI(billingService);
        OrderItemCLI orderItemCLI = new OrderItemCLI(
                new OrderItemService(orderItemRepository, menuItemRepository),
                menuItemService,
                scanner);
        PaymentCLI paymentCLI = new PaymentCLI(
                billingService, billingCLI, scanner);

        boolean running = true;
        while (running) {
            System.out.println("\n=== RESTAURANT MANAGEMENT ===");
            System.out.println("1. View all tables");
            System.out.println("2. View menu");
            System.out.println("3. Create order");
            System.out.println("4. View orders");
            System.out.println("5. Pay for and complete order");
            System.out.println("6. View bills");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> viewTables();
                case "2" -> viewMenu(menuItemService);
                case "3" -> orderItemCLI.createOrder();
                case "4" -> orderItemCLI.viewOrders();
                case "5" -> paymentCLI.start();
                case "6" -> billingCLI.viewBills();
                case "7" -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void viewTables() {
        List<Tables> tables = new TableRepository().getAllTables();
        System.out.println("\n=== TABLES ===");
        tables.forEach(System.out::println);
    }

    private static void viewMenu(MenuItemService menuItemService) {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        System.out.println("\n=== MENU ===");
        menuItems.forEach(menuItem -> System.out.println(
                menuItem.getId() + ". " + menuItem.getName()
                        + " - $" + String.format("%.2f", menuItem.getPrice())));
    }
}
