package com.finance.classes.commands;

import java.util.Scanner;

import com.finance.classes.AppContext;
import com.finance.classes.FileParser;
import com.finance.classes.fabrics.ParserFabric;
import com.finance.interfaces.Command;

public class ExportAllOperations implements Command {
    private AppContext context;

    public ExportAllOperations(AppContext context) {
        this.context = context;
    }

    public boolean execute() {
        System.out.println("Выберите формат экспорта\n1 - csv\n2 - json\n3 - yaml");

        Scanner scanner = context.getScanner();
        String format = scanner.nextLine();
        
        FileParser parser = ParserFabric.getParserById(format);
        
        if (parser == null) {
            System.out.println("Неверный формат экспорта");
            return false;
        }

        System.out.println("Введите имя файла:");
        String filename = scanner.nextLine();

        parser.exportToFile(context.getOperations(), filename);

        System.out.println("Операции успешно экспортированы");
        return true;
    }

    public String getName() {
        return "Экспортировать все операции в файл";
    }
}
