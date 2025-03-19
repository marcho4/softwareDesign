package com.finance.classes.commands;

import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;

public class DeleteCategory implements Command {
    private AppContext context;

    public DeleteCategory(AppContext context) {
        this.context = context;
    }

    @Override
    public boolean execute() {
        var categories = context.getCategories();
        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий.");
            return false;
        }
        
        FinanceApp.printForChoice(categories, "Выберите категорию для удаления");
        int catIdx = FinanceApp.getCorrectChoice(context.getScanner(), categories.size());
        context.deleteCategory(categories.get(catIdx).getName());
        return true;
    }

    @Override
    public String getName() {
        return "Удалить категорию";
    }
    
}
