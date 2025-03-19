package com.finance.classes.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.classes.AppContext;
import com.finance.classes.BankAccFacade;
import com.finance.interfaces.Command;

@Component
public class CreateAccount implements Command {
    private AppContext context;
    private BankAccFacade bankDirectory;

    @Autowired
    public CreateAccount(AppContext appContext, BankAccFacade bankDirectory) {
        this.context = appContext;
        this.bankDirectory = bankDirectory;
    }

    public boolean execute() {
        System.out.println("Введите название счета");
        String name = context.getScanner().nextLine();
        if (name.isEmpty()) {
            System.out.println("Название счета не может быть пустым.");
            return false;
        }
        if (context.getAccounts().stream().anyMatch(a -> a.getName().equals(name))) {
            System.out.println("Счет с таким названием уже существует.");
            return false;
        }
        bankDirectory.createAccount(name);
        return true;
    }

    public String getName() {
        return "Создать счет";
    }

}