package com.example;

public class Config {
    public static final String RED = "\u001B[31m"; 
    public static final String RESET = "\u001B[0m"; 
    public static final String GREEN = "\u001B[32m";
    public String greeting = "\"Йоу мужик! Добро пожаловать в наш скромный зоопарк!";
    public String start_msg = "Что изволишь сделать?\n"
                            + "1 - Добавить зверя доброго или страшного\n"
                            + "2 - Сколько еды нужно на дневную кормежку зоопарка\n" 
                            + "3 - Список добрюшек-зверушек для тискания\n" 
                            + "4 - Отчет о всех зверях\n"
                            + "5 - Отчет о вещичках\n" 
                            + "6 - Мне надоел этот зверинец\n"
                            + "Введи циферку для продолжения и мы выполним твою просьбу! That's how we do it";

    public String err_msg = RED + "No respect to you! Ты ввел неверный номер действия bro" + RESET;
    public String add_animal_err = RED + "Зверюга болеет. Зоопарк таких не терпит" + RESET;

    public String choose_animal = "Выбери зверя, которого хочешь добавить:\n"
                                + "1 - Волчара\n"
                                + "2 - Зайчик\n"
                                + "3 - Бибизянка\n"
                                + "4 - ТИГРилла";
                                
    public String food_msg(int kg) {
        return GREEN + "\nЧтобы насытить живность в вашем зоопарке на 1 день нужно " + kg + " кг отборнейшего корма\n" + RESET;
    }
    public String how_much_food_msg = "Сколько килограммов корма кушает ваша зверюга?";
    public String how_much_goodness_msg = "На сколько добрый ваш милый зверёк?";
    public String is_animal_healthy_msg = "Ваш зверь здоров? (1 - да, 0 - нет, он больной и злой)";
    public String success_msg = GREEN + "УРАААА ТЕПЕРЬ ВАШ ЗВЕРЬ ЖИВЕТ В ЗООПАРКЕ" + RESET;
}
