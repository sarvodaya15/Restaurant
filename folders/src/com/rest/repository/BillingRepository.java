package com.rest.repository;

import com.rest.models.Billing;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillingRepository {
    private final List<Billing> bills = new ArrayList<>();

    public void add(Billing billing) {
        bills.add(billing);
    }

    public List<Billing> findAll() {
        return new ArrayList<>(bills);
    }

    public Optional<Billing> findById(int id) {
        return bills.stream()
                .filter(billing -> billing.id() == id)
                .findFirst();
    }

    public Optional<Billing> findByOrderId(int orderId) {
        return bills.stream()
                .filter(billing -> billing.orderId() == orderId)
                .findFirst();
    }
}
