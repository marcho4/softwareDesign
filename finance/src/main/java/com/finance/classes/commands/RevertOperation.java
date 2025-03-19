package com.finance.classes.commands;

import java.util.List;

import com.finance.classes.AppContext;
import com.finance.classes.FinanceApp;
import com.finance.interfaces.Command;
import com.finance.values.Operation;

public class RevertOperation implements Command {
    private AppContext context;

    public RevertOperation(AppContext appContext) {
        this.context = appContext;
    }

    public boolean execute() {
        List<Operation> operations = context.getOperations();
        if (operations.isEmpty()) {
            System.out.println("Нет доступных операций.");
            return false;
        }
        
        System.out.println("Выберите операцию для отмены");
        int i = 1;
        for (Operation item : operations) {
            System.out.println(i + " - " + item.toString());
            i++;
        }
        int operationIndex = FinanceApp.getCorrectChoice(context.getScanner(), operations.size());

        context.getOperations().remove(operationIndex);
        return true;
    }

    public String getName() {
        return "Отменить операцию";
    }
}
