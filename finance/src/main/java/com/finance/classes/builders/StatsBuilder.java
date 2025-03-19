package com.finance.classes.builders;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.classes.AppContext;
import com.finance.values.Operation;
import com.finance.values.TransactionType;

@Component
public class StatsBuilder {
    private AppContext context;
    private List<Operation> operations;

    @Autowired
    public StatsBuilder(AppContext context) {
        this.context = context;
        operations = context.getOperations();
    }

    public StatsBuilder filterByBankAccount(String bankAccountId) {
        operations = operations.stream()
            .filter(operation -> operation.getBankAccountId().equals(bankAccountId))
            .toList();
        return this;
    }
    public StatsBuilder filterByStartDate(LocalDateTime startDate) {
        if (startDate != null) {
            operations = operations.stream()
                    .filter(op -> op.getDate() != null && !op.getDate().isBefore(startDate))
                    .toList();
        }
        return this;
    }

    public StatsBuilder filterByCategory(String categoryId) {
        operations = operations.stream().filter(op -> op.getCategoryId().equals(categoryId)).toList();
        return this;
    }

    public StatsBuilder filterByEndDate(LocalDateTime endDate) {
        if (endDate != null) {
            operations = operations.stream()
                    .filter(op -> op.getDate() != null && !op.getDate().isAfter(endDate))
                    .toList();
        }
        return this;
    }
    public StatsBuilder filterByOperationType(TransactionType type) {
        operations = operations.stream()
                    .filter(op -> op.getTransactionType() == type)
                    .toList();
        return this;
    }

    public List<Operation> build() {
        var temp = operations;
        operations = context.getOperations();
        return temp;
    }
}
