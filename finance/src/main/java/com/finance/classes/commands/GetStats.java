package com.finance.classes.commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.finance.classes.AppContext;
import com.finance.classes.builders.StatsBuilder;
import com.finance.interfaces.Command;
import com.finance.values.BankAccount;
import com.finance.values.Operation;
import com.finance.values.TransactionType;

public class GetStats implements Command {
    private AppContext context;

    public GetStats(AppContext appContext) {
        this.context = appContext;
    }

    public boolean execute() {
        StatsBuilder builder = new StatsBuilder(context);
        Scanner scanner = context.getScanner();
        
        List<BankAccount> accounts = context.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Нет доступных банковских счетов.");
            return false;
        }

        System.out.println("Выберите банковский счет:");
        System.out.println("0 - Пропустить фильтрацию по счету");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + " - " + accounts.get(i).getName());
        }

        int bankAccountId = -1;
        String input = scanner.nextLine();
        try {
            bankAccountId = Integer.parseInt(input.trim()) - 1;
            if (bankAccountId == -1) {
                bankAccountId = -1;
            } else if (bankAccountId < 0 || bankAccountId >= accounts.size()) {
                System.out.println("Неверный номер банковского аккаунта. Попробуйте снова.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Пожалуйста, введите корректное число.");
            return false;
        }

        if (bankAccountId != -1) {
            builder.filterByBankAccount(accounts.get(bankAccountId).getId());
        }
        
        System.out.println("Выберите категорию (введите id, 0 - для всех):");
        for (int i = 0; i < context.getCategories().size(); i++) {
            System.out.println((i + 1) + " - " + context.getCategories().get(i).getName());
        }
        
        int catId = -1;
        input = scanner.nextLine();

        try {
            catId = Integer.parseInt(input.trim()) - 1;
            if (catId < -1 || catId >= context.getCategories().size()) {
                System.out.println("Неверный номер категории. Попробуйте снова.");
                return false;
            };
        } catch (NumberFormatException e) {
            System.out.println("Пожалуйста, введите корректное число.");
            return false;
        }

        if (catId != -1) {
            builder.filterByCategory(context.getCategories().get(catId).getId());
        }
        
        System.out.println("Введите начальную дату в формате yyyy-MM-dd.\n0 - с начала");
        String startDateInput = scanner.nextLine().trim();
        LocalDateTime startDate = null;
        if (!startDateInput.equals("0")) {
            try {
                LocalDate ld = LocalDate.parse(startDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                startDate = ld.atStartOfDay();
                builder.filterByStartDate(startDate);
            } catch (Exception e) {
                System.out.println("Неверный формат начальной даты.");
                return false;
            }
        }
        
        System.out.println("Введите конечную дату в формате yyyy-MM-dd.\n0 - до конца");
        String endDateInput = scanner.nextLine().trim();
        LocalDateTime endDate = null;
        if (!endDateInput.equals("0")) {
            try {
                LocalDate ld = LocalDate.parse(endDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                endDate = ld.atTime(LocalTime.MAX);
                builder.filterByEndDate(endDate);
            } catch (Exception e) {
                System.out.println("Неверный формат конечной даты.");
                return false;
            }
        }
        
        System.out.println("Введите тип операции:\n0 - Любая\n1 - Трата\n2 - Заработок");
        String opTypeInput = scanner.nextLine().trim().toLowerCase();
        if (!opTypeInput.equals("0") && !opTypeInput.isEmpty()) {
            if (opTypeInput.equals("1")) {
                builder.filterByOperationType(TransactionType.SPENDING);
            } else if (opTypeInput.equals("2")) {
                builder.filterByOperationType(TransactionType.INCOME);
            } else {
                System.out.println("Неверный тип операции.");
                return false;
            }
        }
        
        System.out.println("Введите тип вывода:\n1 - консоль\n2 - файл");
        String outputType = scanner.nextLine().trim().toLowerCase();
        if (!(outputType.equals("1") || outputType.equals("2"))) {
            System.out.println("Неверный тип вывода.");
            return false;
        }
        
        String filePath = null;
        if (outputType.equals("2")) {
            System.out.println("Введите путь к файлу:");
            filePath = scanner.nextLine().trim();
            if (filePath.isEmpty()) {
                System.out.println("Неверный путь к файлу.");
                return false;
            }
        }
        
        
        List<Operation> filteredOps = builder.build();

        if (filteredOps.isEmpty()) {
            System.out.println("Нет операций, удовлетворяющих заданным критериям.");
            return false;
        }
        
        if (outputType.equals("1")) {
            System.out.println("Статистика операций:");
            for (Operation op : filteredOps) {
                System.out.println(op); 
            }
        } else { // file
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
                for (Operation op : filteredOps) {
                    writer.write(op.toString());
                    writer.newLine();
                }
                System.out.println("Статистика успешно записана в файл: " + filePath);
            } catch (IOException e) {
                System.out.println("Ошибка записи в файл: " + e.getMessage());
            }
        }
        return true;
    }

    public String getName() {
        return "Получить статистику";
    }
}
