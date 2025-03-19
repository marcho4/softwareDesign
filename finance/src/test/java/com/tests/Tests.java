package com.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.finance.classes.AppContext;
import com.finance.classes.BankAccFacade;
import com.finance.classes.builders.OperationBuilder;
import com.finance.classes.builders.StatsBuilder;
import com.finance.classes.fabrics.CategoryFabric;
import com.finance.values.BankAccount;
import com.finance.values.Category;
import com.finance.values.Currency;
import com.finance.values.Money;
import com.finance.values.Operation;
import com.finance.values.TransactionType;

public class Tests {
    private AppContext context = new AppContext();

    @Before
    public void setUp() {}

    @Test
    public void ExportJSONTest() {
        var bankAccount = new BankAccount("test");
        var category = new Category(TransactionType.SPENDING, "Coffee");
        context.addAccount(bankAccount);
        context.addCategory(category);
        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 100))
            .setDate(LocalDateTime.now())
            .setDescription("Coffee")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 500))
            .setDate(LocalDateTime.now())
            .setDescription("капучинка")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        context.getJSONParser().exportToFile(context.getOperations(), "operations.json");
    }

    @Test
    public void ExportYAMLTest() {
        var bankAccount = new BankAccount("test");
        var category = new Category(TransactionType.SPENDING, "Coffee");
        context.addAccount(bankAccount);
        context.addCategory(category);
        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 100))
            .setDate(LocalDateTime.now())
            .setDescription("Coffee")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 500))
            .setDate(LocalDateTime.now())
            .setDescription("капучинка")
            .setTransactionType(TransactionType.SPENDING)
            .build());
        context.getYAMLParser().exportToFile(context.getOperations(), "operations.yaml");
    }

    @Test
    public void ExportCSVTest() {
        var bankAccount = new BankAccount("test");
        var category = new Category(TransactionType.SPENDING, "Coffee");
        context.addAccount(bankAccount);
        context.addCategory(category);
        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 100))
            .setDate(LocalDateTime.now())
            .setDescription("Coffee")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 500))
            .setDate(LocalDateTime.now())
            .setDescription("капучинка")
            .setTransactionType(TransactionType.SPENDING)
            .build());
        context.getCSVParser().exportToFile(context.getOperations(), "operations.csv");
    }

    @Test
    public void ImportYAMLTest() {
        var context = new AppContext();
        var operations = context.getYAMLParser().parseFromFile("operations.yaml");
        assertTrue(operations.size() == 2);
    }

    @Test
    public void ImportJSONTest() {
        var context = new AppContext();
        var operations = context.getJSONParser().parseFromFile("operations.json");
        assertTrue(operations.size() == 2);
    }

    @Test
    public void ImportCSVTest() {
        var context = new AppContext();
        var operations = context.getCSVParser().parseFromFile("operations.csv");
        assertTrue(operations.size() == 2);
    }

    @Test
    public void RevertOperationTest() {
        var bankAccount = new BankAccount("test");
        var category = new Category(TransactionType.SPENDING, "Coffee");
        context.addAccount(bankAccount);
        context.addCategory(category);
        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 100))
            .setDate(LocalDateTime.now())
            .setDescription("Coffee")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        context.AddOperation(new OperationBuilder()
            .setBankAccount(bankAccount)
            .setCategoryId(category)
            .setMoney(new Money(Currency.RUB, 500))
            .setDate(LocalDateTime.now())
            .setDescription("капучинка")
            .setTransactionType(TransactionType.SPENDING)
            .build());

        var operations = context.getOperations();
        assertTrue(operations.size() == 2);
        context.deleteTransaction(operations.get(0));
        assertTrue(operations.size() == 1);
    }

    @Test
    public void testStatsFiltering() {

        BankAccount account1 = new BankAccount("account1");
        BankAccount account2 = new BankAccount("account2");
        context.addAccount(account1);
        context.addAccount(account2);
        
        Category coffeeCategory = new Category(TransactionType.SPENDING, "Coffee");
        Category salaryCategory = new Category(TransactionType.INCOME, "Salary");
        context.addCategory(coffeeCategory);
        context.addCategory(salaryCategory);

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            LocalDateTime date = now.minusDays(i);
            context.AddOperation(new OperationBuilder()
                .setBankAccount(account1)
                .setCategoryId(coffeeCategory)
                .setMoney(new Money(Currency.RUB, 100))
                .setDate(date)
                .setDescription("Coffee expense account1 #" + i)
                .setTransactionType(TransactionType.SPENDING)
                .build());
        }

        for (int i = 0; i < 3; i++) {
            LocalDateTime date = now.minusDays(i + 2);
            context.AddOperation(new OperationBuilder()
                .setBankAccount(account2)
                .setCategoryId(coffeeCategory)
                .setMoney(new Money(Currency.RUB, 150))
                .setDate(date)
                .setDescription("Coffee expense account2 #" + i)
                .setTransactionType(TransactionType.SPENDING)
                .build());
        }

        for (int i = 0; i < 4; i++) {
            LocalDateTime date = now.minusDays(i + 1);
            context.AddOperation(new OperationBuilder()
                .setBankAccount(account1)
                .setCategoryId(salaryCategory)
                .setMoney(new Money(Currency.RUB, 1000))
                .setDate(date)
                .setDescription("Salary income #" + i)
                .setTransactionType(TransactionType.INCOME)
                .build());
        }


        StatsBuilder statsBuilder = new StatsBuilder(context);
        List<Operation> account1Ops = statsBuilder.filterByBankAccount(account1.getId()).build();
        assertEquals("Account1 operations count", 9, account1Ops.size());

        statsBuilder = new StatsBuilder(context);
        List<Operation> coffeeOps = statsBuilder.filterByCategory(coffeeCategory.getId()).build();
        assertEquals("Coffee category operations count", 8, coffeeOps.size());

        statsBuilder = new StatsBuilder(context);
        List<Operation> spendingOps = statsBuilder.filterByOperationType(TransactionType.SPENDING).build();
        assertEquals("Spending operations count", 8, spendingOps.size());

        statsBuilder = new StatsBuilder(context);
        LocalDateTime startDate = now.minusDays(2);
        List<Operation> recentOps = statsBuilder.filterByStartDate(startDate).build();
       
        assertEquals("Recent operations count", 6, recentOps.size());
    }

    @Test
    public void BankAccFacadeTest() {
        var acc = new BankAccFacade(context);
        acc.createAccount("test");
        acc.createAccount("test2");
        acc.createAccount("test3");
        acc.createAccount("test4");
        acc.createAccount("test5");
        assertEquals(5, context.getAccounts().size());

        acc.deleteAccount("test");
        assertEquals(4, context.getAccounts().size());

        acc.changeAccountName("test2", "test2_new");
        assertEquals("test2_new", context.getAccounts().get(0).getName());

        acc.deleteAccount("test2_new");
        assertEquals(3, context.getAccounts().size());

        acc.deleteAccount("test3");
        assertEquals(2, context.getAccounts().size());
    }

    @Test
    public void CategoryFabricTest() {
        var operation = CategoryFabric.createCategory("test", TransactionType.SPENDING);
        context.addCategory(operation);
        var operation2 = CategoryFabric.createCategory("test2", TransactionType.SPENDING);
        context.addCategory(operation2);
        var operation3 = CategoryFabric.createCategory("test3", TransactionType.SPENDING);
        context.addCategory(operation3);
        assertEquals(3, context.getCategories().size());

        var operation4 = CategoryFabric.createByIdx("1", "test");
        assertEquals(operation.getTransactionType(), operation4.getTransactionType());
    }

    @Test
    public void OperationBuilderTest() {
        try {
            new OperationBuilder()
            .setBankAccount(new BankAccount("test"))
            .setCategoryId(new Category(TransactionType.SPENDING, "Coffee"))
            .build();
        } catch (Exception e) {
            assertTrue(true);
        }
        
        try {
            new OperationBuilder()
            .setBankAccount(new BankAccount("test"))
            .setCategoryId(new Category(TransactionType.SPENDING, "Coffee"))
            .setMoney(new Money(Currency.RUB, 100))
            .setDate(LocalDateTime.now())
            .setDescription("Coffee")
            .setTransactionType(TransactionType.SPENDING)
            .build();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}