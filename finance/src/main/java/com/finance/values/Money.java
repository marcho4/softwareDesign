package com.finance.values;

public class Money {
    private Currency currency;
    private double amount;

    public Money() {
        this.currency = Currency.RUB;
        this.amount = 0.0;
    }

    public Money(double amount) {
        this.currency = Currency.RUB;
        this.amount = amount;
    }

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", currency, amount);
    }
}