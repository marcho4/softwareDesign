package com.finance.classes.decorators;

import com.finance.interfaces.Command;

public class ResultOutputDecorator implements Command {
    private Command command;

    public ResultOutputDecorator(Command command) {
        this.command = command;
    }

    public boolean execute() {
        if (command.execute()) {
            System.out.println("\u001B[32mУспешно выполнено\u001B[0m");
            return true;
        } else {
            System.out.println("\u001B[31mОшибка при выполнении команды\u001B[0m");
            return false;
        }
    }

    public String getName() {
        return command.getName();
    }
}