package com.rest.services;

import com.rest.models.Tables;
import com.rest.repository.TableRepository;
import java.util.List;

public class TableService {
    private final TableRepository tableRepository;

    public TableService() {
        tableRepository = new TableRepository();
    }

    public List<Tables> getAllTables() {
        return tableRepository.findAll();
    }
}
