package com.example;

// Singleton класс конфига
public class AppConfig {
    private static Config instance;

    public AppConfig() {}

    public AppConfig(Config config) {
        instance = config;
    }
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        };
        return instance;
    }
}