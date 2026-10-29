
package com.rest.repository;

import com.rest.models.Tables;
import java.util.ArrayList;
import java.util.List;

public class TableRepository {

    private final List<Tables> tables = new ArrayList<>();

    public TableRepository() {
        tables.add(new Tables(1, 2));
        tables.add(new Tables(2, 4));
        tables.add(new Tables(3, 6));
    }

    public List<Tables> findAll() {
        return tables;
    }

    public List<Tables> getAllTables() {
        return findAll();
    }
}
