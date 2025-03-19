package com.finance.classes.commands;

import java.util.List;

import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;
import com.finance.values.Category;
import com.finance.values.TransactionType;

public class ChangeCategoryType implements Command {
    private AppContext context;

    public ChangeCategoryType(AppContext context) {
        this.context = context;
    }

    public boolean execute() {
        var scanner = context.getScanner();

        List<Category> categories = context.getCategories();
        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий.");
            return false;
        }
        FinanceApp.printForChoice(categories, "Выберите категорию:");
        int categoryIndex = FinanceApp.getCorrectChoice(scanner, categories.size());

        System.out.println("Введите новый тип категории:\n1 - Расход\n2 - Доход");
        int newType = FinanceApp.getCorrectChoice(scanner, 2) + 1;
        categories.get(categoryIndex).setTransactionType((newType == 1) ? TransactionType.SPENDING : TransactionType.INCOME);
        return true;
    }


    @Override
    public String getName() {
        return "Изменить тип категории";
    }
}
