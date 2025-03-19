package com.finance.classes.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.classes.AppContext;
import com.finance.classes.fabrics.CategoryFabric;
import com.finance.interfaces.Command;
@Component
public class AddCategory implements Command {

    private AppContext context;

    @Autowired
    public AddCategory(AppContext context) {
        this.context = context;
    }

    public boolean execute() {
        System.out.println("Введите название категории, которую хотите добавить:");
        
        String categoryName = context.getScanner().nextLine();
        if (categoryName.equals("")) {
            System.out.println("Название категории не может быть пустым");
            return false;
        }
        System.out.println("Введите тип категории\n1 - Трата\n2 - Доход");
        String categoryType = context.getScanner().nextLine();

        var category = CategoryFabric.createByIdx(categoryType, categoryName);

        if (category == null) {
            System.out.println("Неверный тип категории");
            return false;
        }

        context.addCategory(category);
        return true;
    }

    public String getName() {
        return "Добавить категорию";
    }
}