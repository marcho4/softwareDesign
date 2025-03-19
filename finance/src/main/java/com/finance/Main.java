package com.finance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.finance.classes.FinanceApp;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.finance");
        FinanceApp app = context.getBean(FinanceApp.class);
        app.run();
    }
}