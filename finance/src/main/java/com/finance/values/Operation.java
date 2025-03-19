package com.finance.values;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class Operation {
    private String id;
    private TransactionType transactionType;
    private String bankAccountId;
    private Money amount;
    private LocalDateTime date;
    private String description;
    private String categoryId;

    public Operation() {
        this.id = UUID.randomUUID().toString();
    }
    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }
    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
    public void setAmount(Money amount) {
        this.amount = amount;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public String getBankAccountId() {
        return bankAccountId;
    }
    public Money getAmount() {
        return amount;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        StringBuilder sb = new StringBuilder();
        sb.append("Operation {\n");
        sb.append("  ID: ").append(id).append("\n");
        sb.append("  Type: ").append(transactionType != null ? transactionType.toString() : "N/A").append("\n");
        sb.append("  Account: ").append(bankAccountId != null ? bankAccountId : "N/A").append("\n");
        sb.append("  Amount: ").append(amount != null ? amount.toString() : "N/A").append("\n");
        sb.append("  Date: ").append(date != null ? date.format(formatter) : "N/A").append("\n");
        sb.append("  Description: ").append(description != null ? description : "N/A").append("\n");
        sb.append("  Category: ").append(categoryId != null ? categoryId : "N/A").append("\n");
        sb.append("}");
        
        return sb.toString();
    }
}
