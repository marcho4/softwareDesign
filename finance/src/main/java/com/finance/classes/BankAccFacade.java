package com.finance.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finance.values.BankAccount;

@Component
public class BankAccFacade {
    private AppContext context;

    @Autowired
    public BankAccFacade(AppContext context) {
        this.context = context;
    }

    public void createAccount(String name) {
        context.addAccount(new BankAccount(name));
    }

    public void deleteAccount(String name) {
        context.deleteAccount(name);
    }

    public void changeAccountName(String name, String newName) {
        context.changeBankAccName(name, newName);
    }
}
