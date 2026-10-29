package com.rest.services;

import com.rest.models.MenuItem;
import com.rest.repository.MenuItemRepository;
import java.util.List;
import java.util.Optional;

public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItem(int id) {
        return menuItemRepository.findById(id);
    }

    public void createMenuItem(int id, String name, double price) {
        validateMenuItem(id, name, price);
        if (menuItemRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("A menu item with that ID already exists.");
        }
        menuItemRepository.add(new MenuItem(id, name.trim(), price));
    }

    public void updateMenuItem(int id, String name, double price) {
        validateMenuItem(id, name, price);
        if (!menuItemRepository.update(new MenuItem(id, name.trim(), price))) {
            throw new IllegalArgumentException("Menu item not found.");
        }
    }

    public void deleteMenuItem(int id) {
        if (!menuItemRepository.deleteById(id)) {
            throw new IllegalArgumentException("Menu item not found.");
        }
    }

    private void validateMenuItem(int id, String name, double price) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (!Double.isFinite(price) || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    }
}
