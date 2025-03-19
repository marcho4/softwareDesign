package com.finance.values;
import java.util.UUID;

import com.finance.interfaces.HasName;
public class BankAccount implements HasName {
    private String id;
    private String name;
    private Money balance;

    public BankAccount(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.balance = new Money();
    }

    public BankAccount(String name, Money balance) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.balance = balance;
    }

    public BankAccount(String name, Currency currency, double amount) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.balance = new Money(currency, amount);
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getBalance() {
        return balance;
    }
    public Currency getCurrency() {
        return balance.getCurrency();
    }
    public double getAmount() {
        return balance.getAmount();
    }   
}
