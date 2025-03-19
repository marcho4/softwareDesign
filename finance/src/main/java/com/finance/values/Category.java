package com.finance.values;
import java.util.UUID;

import com.finance.interfaces.HasName;


public class Category implements HasName {
    private String id;
    private TransactionType type;
    private String name;

    public Category(TransactionType type, String name) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public TransactionType getTransactionType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название категории не может быть пустым");
        }
        this.name = name;
    }
    public void setTransactionType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Категория: " + name + " (" + type + ")";
    }
}