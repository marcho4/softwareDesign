package com.finance.classes.fabrics;

import org.springframework.stereotype.Component;

import com.finance.values.Category;
import com.finance.values.TransactionType;

@Component
public class CategoryFabric {
    public static Category createIncomeCategory(String name) {
        return new Category(TransactionType.INCOME, name);
    }
    public static Category createSpendingCategory(String name) {
        return new Category(TransactionType.SPENDING, name);
    }
    public static Category createByIdx(String idx, String name) {
        switch (idx) {
            case "1":
                return createSpendingCategory(name);
            case "2":
                return createIncomeCategory(name);
            default:
                return null;
        }
    }
    public static Category createCategory(String name, TransactionType type) {
        return new Category(type, name);
    }
}
