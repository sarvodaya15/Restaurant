package com.rest.repository;

import com.rest.models.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository {
    private final List<MenuItem> menuItems = new ArrayList<>();

    public MenuItemRepository() {
        menuItems.add(new MenuItem(1, "Burger", 8.99));
        menuItems.add(new MenuItem(2, "Pizza", 12.99));
        menuItems.add(new MenuItem(3, "Pasta", 10.99));
        menuItems.add(new MenuItem(4, "French Fries", 4.99));
        menuItems.add(new MenuItem(5, "Caesar Salad", 7.99));
        menuItems.add(new MenuItem(6, "Grilled Chicken", 13.99));
        menuItems.add(new MenuItem(7, "Chocolate Cake", 5.99));
        menuItems.add(new MenuItem(8, "Coffee", 2.99));
    }

    public List<MenuItem> findAll() {
        return new ArrayList<>(menuItems);
    }

    public Optional<MenuItem> findById(int id) {
        return menuItems.stream()
                .filter(menuItem -> menuItem.getId() == id)
                .findFirst();
    }

    public void add(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public boolean update(MenuItem updatedMenuItem) {
        for (int index = 0; index < menuItems.size(); index++) {
            if (menuItems.get(index).getId() == updatedMenuItem.getId()) {
                menuItems.set(index, updatedMenuItem);
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(int id) {
        return menuItems.removeIf(menuItem -> menuItem.getId() == id);
    }
}
