package com.finance.classes.commands;

import java.util.List;

import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;
import com.finance.values.Category;

public class ChangeCategoryName implements Command {
    private AppContext context;

    public ChangeCategoryName(AppContext context) {
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

        System.out.println("Введите новое название категории:");
        String newName = scanner.nextLine();
        try {
            categories.get(categoryIndex).setName(newName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String getName() {
        return "Изменить название категории";
    }
}
