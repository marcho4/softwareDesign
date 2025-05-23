# БДЗ Дергилёв Марк БПИ 236

## Паттерны, используемые в программе

1. Builder для Operation и Stats ✅
    * Удобнее накидывать какие-то изменения много раз для сбора статистики
2. Шаблонный метод для FileParser'ов ✅
    * Абстракция логики работы с файловыми парсерами. Достаточно переписать метод обработки входных данных, а остальным не надо заниматься
3. Фасад для BankAccount ✅
    * Управление созданием и функциями BankAccount в одном месте 
4. Команда для действий в интерфейсе ✅
5. Фабрика для Category и Parser ✅
     * Помогает избежать дополнительных if в коде, вся логика создания категории происходит внутри фабрики
6. Декоратор ✅
    * Благодаря нему можно добавить вывод сообщения об успешности команды в консоль
    * Реализовано через интерфейс Command

## Соблюдение принципов ООП и GRASP

1. Инкапсуляция - в каждых классе все поля приватные и доступ обеспечивается через геттеры и сеттеры
2. Low coupling - за счет использования DI контейнера Spring
3. Посредничество - за счет использования класса FinanceApp, который является фасадом для работы с внутренними объектами
4. При работе с наследованием были применены принципы SOLID во всех реализованных паттернах
5. High Cohesion - классы отвечают только за свою функцию

## Описание классов
FinanceApp - класс для интерфейса работы с программой и вызова команд
AppContext - класс для работы с данными, сохраннеными пользователем. Вместо него по-хорошему использовать базу данныхё
BankAccFacade - отвечает за создание, редактирование и удаление банковских аккаунтов
ResultOutputDecorator - отвечает за вывод результата команды в консоль
ParserFabric - создает парсер по индексу или по названию типа файла
StatsBuilder - фильтрует операции по заданному пользователем критерию

## Описание решения

Программа позволяет создать аккаунт, выгружать операции в формате CSV, JSON, YAML,
собирать общую статистику трат и так же по отдельным категориям, удалять аккаунт. Выбрал вариант хранения операций в файликах, чтобы можно было глянуть отчет по ним, так как это самые информативные объекты.

Вместо базы данных будет AppContex, который будет внедряться в модули, в которых необходим доступ к данным, с помощью DI контейнера, также AppContext - Singleton.
В будущем AppContext может стать репозиторием для базы данных

* Import/Export JSON ✅
* Import/Export CSV ✅
* Import/Export YAML ✅
* Tests - Stats | Import/Export | Bank Accounts creation/deletion | Operation builder ✅
* DI container ✅
* 6 patterns ✅
* CRUD BankAccounts ✅
* CRUD Categories ✅
* CRUD Operations ✅
* ReadMe ✅

## Инструкция по запуску
``` sudo apt install maven``` (Linux) или ```brew install maven``` (Mac OS)


```mvn clean install``` в директории проекта


```mvn spring-boot:run``` для запуска приложения
