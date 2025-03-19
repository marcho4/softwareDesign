package com.example;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.animals.Monkey;
import com.example.animals.Rabbit;
import com.example.animals.Tiger;
import com.example.animals.Wolf;
import com.example.interfaces.IInventory;

public class App {

    // Утилитный метод для безопасного ввода целого числа
    public static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Некорректный ввод. Введите число:");
            scanner.next(); // Пропускаем неверный ввод
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");
        Zoo zoo = context.getBean(Zoo.class);
        Scanner scanner = new Scanner(System.in);
        Config conf = AppConfig.getInstance();

        System.out.println(conf.greeting);

        while (true) {
            System.out.println(conf.start_msg);
            int choice = readInt(scanner);

            if (choice == 6) {
                scanner.close();
                break;
            }

            switch (choice) {
                case 1:
                    // Выбор типа животного
                    System.out.println(conf.choose_animal);
                    int animalChoice = readInt(scanner);

                    // Если выбор вне диапазона 1-4, выводим сообщение и переходим к следующей итерации
                    if (animalChoice < 1 || animalChoice > 4) {
                        System.out.println(conf.err_msg);
                        break;
                    }

                    // Запрос количества еды
                    System.out.println(conf.how_much_food_msg);
                    int food = readInt(scanner);
                    if (food <= 0) {
                        System.out.println(conf.err_msg);
                        break;
                    }

                    // Запрос состояния здоровья
                    System.out.println(conf.is_animal_healthy_msg);
                    int healthInput = readInt(scanner);
                    while (healthInput != 0 && healthInput != 1) {
                        System.out.println(conf.is_animal_healthy_msg + " (1 - да, 0 - нет)");
                        healthInput = readInt(scanner);
                    }
                    boolean isHealthy = (healthInput == 1);

                    Animal animal = null;

                    // Если выбранное животное является травоядным (например, Зайчик или Бибизянка)
                    if (animalChoice == 2 || animalChoice == 3) {
                        System.out.println(conf.how_much_goodness_msg);
                        int goodness = readInt(scanner);
                        while (goodness < 0 || goodness > 10) {
                            System.out.println(conf.how_much_goodness_msg);
                            goodness = readInt(scanner);
                        }
                        if (animalChoice == 2) {
                            animal = new Rabbit(food, isHealthy, goodness);
                        } else { // animalChoice == 3
                            animal = new Monkey(food, isHealthy, goodness);
                        }
                    } else if (animalChoice == 1) {
                        animal = new Wolf(food, isHealthy);
                    } else if (animalChoice == 4) {
                        animal = new Tiger(food, isHealthy);
                    }

                    // Если животное было создано, добавляем его в зоопарк
                    if (animal != null) {
                        if (animal.addToTheZoo(zoo)) {
                            System.out.println(conf.success_msg);
                        }
                    }
                    break;
                case 2:
                    // Сколько еды нужно на дневную кормежку
                    System.out.println(conf.food_msg(zoo.getDailyFood()));
                    break;
                case 3:
                    if (zoo.getCutties().isEmpty()) {
                        System.out.println("Нет добрюшек-зверюшек для тискания");
                    } else {
                        System.out.println("---------------------------------");
                        for (int i = 0; i < zoo.getCutties().size(); i++) {
                            System.out.println(i + 1 + ". " + zoo.getCutties().get(i).toString());
                            System.out.println("---------------------------------");
                        }
                    }
                    break;
                case 4:
                    // Отчет о всех зверях
                    if (zoo.getAllAnimals().isEmpty()) {
                        System.out.println("Нет зверей :(");
                    } else {
                        System.out.println("---------------------------------");

                        for (int i = 0; i < zoo.getAllAnimals().size(); i++) {
                            System.out.println(i + 1 + ". " + zoo.getAllAnimals().get(i).toString());
                            System.out.println("---------------------------------");
                        }
                    }
                    break;
                case 5:
                    // Отчет о вещичках
                    if (zoo.getInventories().isEmpty()) {
                        System.out.println("Нет вещей. Ну вы нищие конечно :(");
                    } else {
                        for (IInventory item : zoo.getInventories()) {
                            System.out.println(item.toString());
                        }
                    }
                    break;
                default:
                    System.out.println(conf.err_msg);
            }
        }

    }
}