package com.finance.classes.commands;

import java.util.List;

import com.finance.classes.AppContext;
import com.finance.classes.BankAccFacade;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;
import com.finance.values.BankAccount;

public class DeleteAccount implements Command {
    private AppContext context;
    private BankAccFacade bankDirectory;

    public DeleteAccount(AppContext appContext, BankAccFacade bankDirectory) {
        this.context = appContext;
        this.bankDirectory = bankDirectory;
    }

    public boolean execute() {
        var scanner = context.getScanner();
        List<BankAccount> accounts = context.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Нет доступных банковских счетов.");
            return false;
        }
        FinanceApp.printForChoice(accounts, "Выберите банковский счет:");
        int bankAccountIndex = FinanceApp.getCorrectChoice(scanner, accounts.size());        
        
        bankDirectory.deleteAccount(accounts.get(bankAccountIndex).getName());
        return true;
    }

    public String getName() {
        return "Удалить счет";
    }
}