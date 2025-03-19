package com.finance.classes.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.finance.classes.FileParser;
import com.finance.values.Currency;
import com.finance.values.Money;
import com.finance.values.Operation;
import com.finance.values.TransactionType;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class CSVParser extends FileParser {
    private Money parseMoney(String moneyString) {
        String[] parts = moneyString.split(" ");
        Currency currency = Currency.valueOf(parts[0]);
        double amount = Double.parseDouble(parts[1]);
        return new Money(currency, amount);
    }

    @Override
    public void exportToFile(List<Operation> operations, String filename) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeNext(new String[]{"ID", "Transaction type", "Description" ,"Amount", "Category ID", "Date", "Bank Account ID"});

            for (Operation operation : operations) {
                writer.writeNext(new String[]{
                    operation.getId(),
                    operation.getTransactionType().toString(),
                    operation.getDescription(),
                    String.valueOf(operation.getAmount().getCurrency()) + " " + String.valueOf(operation.getAmount().getAmount()),
                    operation.getCategoryId(),
                    operation.getDate().toString(),
                    operation.getBankAccountId()
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Operation> parseFromString(String content) {
        List<Operation> operations = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new StringReader(content))) {
            String[] nextLine;

            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                Operation operation = new Operation();
                operation.setId(nextLine[0]);
                operation.setTransactionType(TransactionType.valueOf(nextLine[1]));
                operation.setDescription(nextLine[2]);
                operation.setAmount(parseMoney(nextLine[3]));
                operation.setCategoryId(nextLine[4]);
                operation.setDate(LocalDateTime.parse(nextLine[5]));
                operation.setBankAccountId(nextLine[6]);

                operations.add(operation);
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return operations;
    }
    
}