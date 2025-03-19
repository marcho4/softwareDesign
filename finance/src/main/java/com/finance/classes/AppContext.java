package com.finance.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.finance.classes.parsers.CSVParser;
import com.finance.classes.parsers.JSONParser;
import com.finance.classes.parsers.YAMLParser;
import com.finance.values.BankAccount;
import com.finance.values.Category;
import com.finance.values.Operation;

@Component
public class AppContext {
    private List<Category> categories;
    private List<BankAccount> accounts;
    private List<Operation> operations;
    private CSVParser parser = new CSVParser();
    private YAMLParser yamlParser = new YAMLParser();
    private JSONParser jsonParser = new JSONParser();
    private Scanner scanner = new Scanner(System.in);

    public AppContext() {
        this.categories = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    // Getters
    public List<Category> getCategories() {
        return categories;
    }
    public List<BankAccount> getAccounts() {
        return accounts;
    }
    public List<Operation> getOperations() {
        return operations;
    }
    public Scanner getScanner() {
        return scanner;
    }

    public void changeBankAccName(String name, String newName) {
        accounts
            .stream()
            .filter(a -> a.getName().equals(name))
            .findFirst()
            .ifPresent(a -> a.setName(newName));
    }
    
    // Adders
    public void addCategory(Category category) {
        categories.add(category);
    }
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }
    public void AddOperation(Operation operation) {
        operations.add(operation);
    }

    // Deleters
    public void deleteCategory(String name) {
        categories.stream().filter(c -> c.getName().equals(name)).findFirst().ifPresent(categories::remove);
    }
    public void deleteAccount(String name) {
        accounts.stream().filter(a -> a.getName().equals(name)).findFirst().ifPresent(accounts::remove);
    }
    public void deleteTransaction(Operation transaction) {
        operations.remove(transaction);
    }

    // Parsers
    public CSVParser getCSVParser() {
        return parser;
    }
    public YAMLParser getYAMLParser() {
        return yamlParser;
    }
    public JSONParser getJSONParser() {
        return jsonParser;
    }
}