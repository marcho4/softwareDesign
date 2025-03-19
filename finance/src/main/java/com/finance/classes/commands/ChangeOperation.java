package com.finance.classes.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;
import com.finance.values.BankAccount;
import com.finance.values.Currency;
import com.finance.values.Money;
import com.finance.values.Operation;

public class ChangeOperation implements Command {
    private AppContext context;

    public ChangeOperation(AppContext context) {
        this.context = context;
    }

    @Override
    public boolean execute() {
        var operations = context.getOperations();
        if (operations.isEmpty()) {
            System.out.println("Нет доступных операций.");
            return false;
        }

        System.out.println("Выберите операцию для изменения:");
        int i = 1;
        for (Operation operation : operations) {
            System.out.println(i + " - " + operation.toString());
            i++;
        }

        int operationIndex = FinanceApp.getCorrectChoice(context.getScanner(), operations.size());
        System.out.println("Выберите поле для изменения:");
        System.out.println("1 - Дата");
        System.out.println("2 - Сумма");
        System.out.println("3 - Категория");
        System.out.println("4 - Описание");
        System.out.println("5 - Поменять валюту");
        System.out.println("6 - Поменять банковский аккаунт");
        int fieldIndex = FinanceApp.getCorrectChoice(context.getScanner(), 6) + 1;
        var operation = operations.get(operationIndex);

        switch (fieldIndex) {
            case 1:
                System.out.println("Введите новую дату в формате дд.мм.гггг чч:мм: ");
                try {
                    var date = LocalDateTime.parse(context.getScanner().nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    operation.setDate(date);
                } catch (DateTimeParseException e) {
                    System.out.println("Неверный формат даты");
                    return false;
                }
                break;
            case 2: 
                System.out.println();
                Double newAmount = FinanceApp.getCorrectDouble("Введите новую сумму:", context.getScanner());
                var money = new Money(operation.getAmount().getCurrency(), newAmount);
                operation.setAmount(money);
                break;
            case 3:
                FinanceApp.printForChoice(context.getCategories(), "Выберите категорию:");
                var category = context.getCategories().get(FinanceApp.getCorrectChoice(context.getScanner(), context.getCategories().size()));
                operation.setCategoryId(category.getId());
                break;
            case 4:
                System.out.println("Введите новое описание:");
                operation.setDescription(context.getScanner().nextLine());
                break;
            case 5:
                System.out.println("Выберите новую валюту:");
                i = 1;
                for (Currency currency : Currency.values()) {
                    System.out.println(i + " - " + currency.name());
                    i++;
                }
                var currency = Currency.values()[FinanceApp.getCorrectChoice(context.getScanner(), Currency.values().length)];
                operation.getAmount().setCurrency(currency);
                break;
            case 6:
                System.out.println("Выберите новый банковский аккаунт:");
                i = 1;
                for (BankAccount bankAccount : context.getAccounts()) {
                    System.out.println(i + " - " + bankAccount.getName());
                    i++;
                }
                var bankAccount = context.getAccounts().get(FinanceApp.getCorrectChoice(context.getScanner(), context.getAccounts().size()));
                operation.setBankAccountId(bankAccount.getId());
                break;
            default:
                System.out.println("Неверный выбор поля");
                return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "Изменить операцию";
    }
}
