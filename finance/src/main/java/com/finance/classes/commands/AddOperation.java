package com.finance.classes.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.finance.values.BankAccount;
import com.finance.values.Category;
import com.finance.values.Currency;
import com.finance.values.Money;
import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.classes.builders.OperationBuilder;
import com.finance.interfaces.Command;


public class AddOperation implements Command {
    private AppContext context;
    public AddOperation(AppContext appContext) {
        this.context = appContext;
    }

    public boolean execute() {
        OperationBuilder builder = new OperationBuilder();
        Scanner scanner = context.getScanner();

        List<BankAccount> accounts = context.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Нет доступных банковских счетов.");
            return false;
        }

        FinanceApp.printForChoice(accounts, "Выберите банковский счет:");
        
        int bankAccountIndex = FinanceApp.getCorrectChoice(scanner, accounts.size());

        builder.setBankAccount(accounts.get(bankAccountIndex));

        List<Category> categories = context.getCategories();
        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий операций.");
            return false;
        }

        FinanceApp.printForChoice(categories, "Выберите категорию:");
        
        int categoryIndex = FinanceApp.getCorrectChoice(scanner, categories.size());

        builder.setCategoryId(categories.get(categoryIndex));
        builder.setTransactionType(categories.get(categoryIndex).getTransactionType());

        double amount = FinanceApp.getCorrectDouble("Введите сумму операции: ", scanner);

        Currency[] currencies = Currency.values();
        System.out.println("Выберите валюту операции:");
        for (int i = 0; i < currencies.length; i++) {
            System.out.println((i + 1) + " - " + currencies[i].name());
        }
        
        int currencyIndex = FinanceApp.getCorrectChoice(scanner, currencies.length);

        Currency currency = currencies[currencyIndex];
        builder.setMoney(new Money(currency, amount));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime operationDate = null;
        while (true) {
            System.out.print("Введите дату операции в формате дд.мм.гггг чч:мм: ");
            String dateInput = scanner.nextLine();
            try {
                operationDate = LocalDateTime.parse(dateInput.trim(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Попробуйте снова.");
            }
        }
        builder.setDate(operationDate);

        String description = "";
        System.out.print("Введите описание операции: ");
        description = scanner.nextLine();
        builder.setDescription(description);

        try {
            context.AddOperation(builder.build());
            System.out.println("Операция успешно добавлена.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public String getName() {
        return "Добавить операцию";
    }
}