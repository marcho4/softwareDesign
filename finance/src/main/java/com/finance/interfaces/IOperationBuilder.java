package com.finance.interfaces;


import java.time.LocalDateTime;

import com.finance.values.BankAccount;
import com.finance.values.Category;
import com.finance.values.Money;
import com.finance.values.Operation;
import com.finance.values.TransactionType;

public interface IOperationBuilder {
    IOperationBuilder reset();
    IOperationBuilder setMoney(Money money);
    IOperationBuilder setTransactionType(TransactionType type);
    IOperationBuilder setCategoryId(Category category);
    IOperationBuilder setBankAccount(BankAccount bankAccount);
    IOperationBuilder setDescription(String description);
    IOperationBuilder setDate(LocalDateTime date);
    Operation build();
}