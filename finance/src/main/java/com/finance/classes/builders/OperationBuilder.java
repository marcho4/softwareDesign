package com.finance.classes.builders;

import com.finance.values.BankAccount;
import com.finance.values.Category;
import com.finance.values.Money;
import com.finance.values.Operation;
import com.finance.interfaces.IOperationBuilder;
import com.finance.values.TransactionType;

import java.time.LocalDateTime;


public class OperationBuilder implements IOperationBuilder {
    private Operation temp = new Operation();

    @Override
    public IOperationBuilder reset() {
        temp = new Operation();
        return this;
    }

    @Override
    public IOperationBuilder setTransactionType(TransactionType type) {
        temp.setTransactionType(type);
        return this;

    }

    @Override
    public IOperationBuilder setCategoryId(Category category) {
        temp.setCategoryId(category.getId());
        return this;

    }

    @Override
    public IOperationBuilder setBankAccount(BankAccount bankAccount) {
        temp.setBankAccountId(bankAccount.getId());
        return this;

    }

    @Override
    public IOperationBuilder setDescription(String description) {
        temp.setDescription(description);
        return this;
    }

    private boolean validate() {
        if (temp.getTransactionType() == null) {
            return false;
        }
        if (temp.getAmount() == null || temp.getAmount().getAmount() <= 0) {
            return false;
        }
        if (temp.getDescription() == null) {
            return false;
        }
        if (temp.getCategoryId() == null || temp.getCategoryId().isEmpty()) {
            return false;
        }
        if (temp.getBankAccountId() == null || temp.getBankAccountId().isEmpty()) {
            return false;
        }
        if (temp.getDate() == null) {
            return false;
        }
        return true;
    }

    @Override
    public Operation build() {
        if (!validate()) {
            throw new IllegalArgumentException("Не достает необходимых данных для создания операции");
        }
        var temp = this.temp;
        this.temp = new Operation();
        return temp;
    }
    @Override
    public IOperationBuilder setMoney(Money money) {
        temp.setAmount(money);
        return this;
    }
    @Override
    public IOperationBuilder setDate(LocalDateTime date) {
        temp.setDate(date);
        return this;
    }
}