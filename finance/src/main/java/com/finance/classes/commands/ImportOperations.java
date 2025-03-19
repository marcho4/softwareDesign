package com.finance.classes.commands;

import java.io.File;

import com.finance.classes.AppContext;
import com.finance.classes.FileParser;
import com.finance.classes.fabrics.ParserFabric;
import com.finance.interfaces.Command;

public class ImportOperations implements Command {
    private AppContext context;

    public ImportOperations(AppContext appContext) {
        this.context = appContext;
    }

    public boolean execute() {
        System.out.println("Введите путь к файлу");
        String path = context.getScanner().nextLine();
        System.out.println("Введите тип файла (csv/json/yaml)");
        String type = context.getScanner().nextLine();

        FileParser parser = ParserFabric.getParser(type);

        if (parser == null) {
            System.out.println("Неверный тип файла");
            return false;
        }
        
        File file = new File(path);

        if (file.exists()) {
            var parsedOperations = parser.parseFromFile(path);
            context.getOperations().addAll(parsedOperations);
            System.out.println("Операции успешно импортированы");
        } else {
            System.out.println("Файл не найден");
            return false;
        }

        return true;
    }

    public String getName() {
        return "Импортировать операции из файла";
    }
}
