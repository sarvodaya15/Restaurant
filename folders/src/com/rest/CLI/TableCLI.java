package com.rest.CLI;

import com.rest.models.Tables;
import com.rest.repository.TableRepository;
import java.util.List;
import java.util.Scanner;

public class TableCLI {
    private final TableRepository service;
    private final Scanner scanner;

    public TableCLI(TableRepository service) {
        this(service, new Scanner(System.in));
    }

    public TableCLI(TableRepository service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== TABLE MANAGEMENT ===");
            System.out.println("1. View all tables");
            System.out.println("0. Back");

            String input = scanner.nextLine();
            if ("1".equals(input)) {
                List<Tables> tables = service.getAllTables();
                System.out.println("\n=== TABLES LIST ===");
                tables.forEach(System.out::println);
            } else if ("0".equals(input)) {
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
    