package com.rest.CLI;

import com.rest.models.MenuItem;
import com.rest.services.MenuItemService;
import java.util.Scanner;

public class MenuItemCLI {
    private final MenuItemService menuItemService;
    private final Scanner scanner;

    public MenuItemCLI(MenuItemService menuItemService, Scanner scanner) {
        this.menuItemService = menuItemService;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU ITEM MANAGEMENT ===");
            System.out.println("1. View all menu items");
            System.out.println("2. Find menu item by ID");
            System.out.println("3. Add menu item");
            System.out.println("4. Update menu item");
            System.out.println("5. Delete menu item");
            System.out.println("0. Back");

            switch (readInt("Enter your choice: ")) {
                case 1 -> {
                    viewMenuItems();
                    running = false;
                }
                case 2 -> findMenuItem();
                case 3 -> createMenuItem();
                case 4 -> updateMenuItem();
                case 5 -> deleteMenuItem();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewMenuItems() {
        var menuItems = menuItemService.getAllMenuItems();
        if (menuItems.isEmpty()) {
            System.out.println("No menu items found. Choose Manage menu items and add one first.");
            return;
        }

        System.out.println("\n=== MENU ITEMS ===");
        menuItems.forEach(System.out::println);
    }

    private void findMenuItem() {
        int id = readInt("Enter menu item ID: ");
        menuItemService.getMenuItem(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Menu item not found."));
    }

    private void createMenuItem() {
        int id = readInt("Enter ID: ");
        String name = readText("Enter name: ");
        double price = readDouble("Enter price: ");
        runAction(() -> menuItemService.createMenuItem(id, name, price));
    }

    private void updateMenuItem() {
        int id = readInt("Enter menu item ID to update: ");
        String name = readText("Enter new name: ");
        double price = readDouble("Enter new price: ");
        runAction(() -> menuItemService.updateMenuItem(id, name, price));
    }

    private void deleteMenuItem() {
        int id = readInt("Enter menu item ID to delete: ");
        runAction(() -> menuItemService.deleteMenuItem(id));
    }

    private void runAction(Runnable action) {
        try {
            action.run();
            System.out.println("Operation successful.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
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

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Enter a valid price.");
            }
        }
    }
}
