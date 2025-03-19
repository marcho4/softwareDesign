package com.finance.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.classes.commands.*;
import com.finance.classes.decorators.ResultOutputDecorator;
import com.finance.interfaces.Command;
import com.finance.interfaces.HasName;

@Component
public class FinanceApp {
    private List<Command> commands = new ArrayList<Command>();
    Scanner scanner;

    @Autowired
    public FinanceApp(AppContext appContext, BankAccFacade bankDirectory) {
        scanner = appContext.getScanner();
        commands.add(new ResultOutputDecorator(new CreateAccount(appContext, bankDirectory)));
        commands.add(new ResultOutputDecorator(new ChangeAccount(appContext, bankDirectory)));
        commands.add(new ResultOutputDecorator(new DeleteAccount(appContext, bankDirectory)));

        commands.add(new ResultOutputDecorator(new AddCategory(appContext)));
        commands.add(new ResultOutputDecorator(new ChangeCategoryName(appContext)));
        commands.add(new ResultOutputDecorator(new ChangeCategoryType(appContext)));
        commands.add(new ResultOutputDecorator(new DeleteCategory(appContext)));

        commands.add(new ResultOutputDecorator(new AddOperation(appContext)));
        commands.add(new ResultOutputDecorator(new RevertOperation(appContext)));
        commands.add(new ResultOutputDecorator(new ChangeOperation(appContext)));

        commands.add(new ResultOutputDecorator(new ExportAllOperations(appContext)));
        commands.add(new ResultOutputDecorator(new ImportOperations(appContext)));
        commands.add(new ResultOutputDecorator(new GetStats(appContext)));
    }

    private void printCommands() {
        System.out.println("Доступные команды:");
        System.out.println("0 - Выход");
        for (int i = 0; i < commands.size(); i++) {
            System.out.println((i + 1) + " - " + (commands.get(i)).getName());
        }
    }

    public static <T extends Object & HasName> void printForChoice(Iterable<T> array, String msg) {
        System.out.println(msg);
        int i = 1;
        for (T item : array) {
            System.out.println(i + " - " + item.getName());
            i++;
        }
    }

    public static int getCorrectChoice(Scanner scanner, int size) {
        int res = -1;
        while (true) {
            String input = scanner.nextLine();
            try {
                res = Integer.parseInt(input.trim()) - 1;
                if (res < 0 || res >= size) {
                    System.out.println("Неверный номер. Попробуйте снова.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
        return res;
    }

    public static double getCorrectDouble(String msg, Scanner scanner) {
        double amount = 0;
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine();
            try {
                amount = Double.parseDouble(input.trim());
                if (amount <= 0) {
                    System.out.println("Сумма должна быть положительной. Попробуйте снова.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
        return amount;
    }

    public void run() {
        System.out.println("Добро пожаловать в финансовое приложение!");
        
        while (true) {
            printCommands();
            System.out.println("Введите номер команды:");
    
            String userInput = scanner.nextLine();
            int commandNumber;
    
            try {
                commandNumber = Integer.parseInt(userInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Пожалуйста, введите корректное число.");
                continue;
            }
    
            if (commandNumber == 0) {
                System.out.println("Завершение работы приложения.");
                break;
            }
    
            if (commandNumber < 1 || commandNumber > commands.size()) {
                System.out.println("Неверный номер команды. Попробуйте снова.");
                continue;
            }
    
            Command command = commands.get(commandNumber - 1);
            try {
                command.execute();
            } catch (Exception e) {
                System.out.println("При выполнении команды произошла ошибка: " + e.getMessage());
            }
        }
    }
}