package ru.r5am;

import org.aeonbits.owner.Config;

@Config.Sources({ "file:app.config" })
public interface AppConfig extends Config {

    // URL
    @DefaultValue("https://ya.ru")
    String scrapUrl();

    // Размер окна браузера
    @DefaultValue("1920x1080")
    String browserSize();

    // Используемый браузер
    @DefaultValue("chrome")
    String selenideBrowser();

    // Директория для отчётов
    @DefaultValue("")
    String reportDir();

    // Параметры объектов
    int minArea();
    int maxArea();
    int minRentalPeriod();
    String propertyType();
    String contractType();
    String country();
    String propertyLocation();
    String sortFieldName();
    int averageRental();
    int profitMonths();
    int priorRepair();
    int contractRegistration();
    int runningCost();
//    int yearlyInsurance();
    int insurance1metre();
    int monthlyHeating();
    int housingOfficeMaintenance();
    int accountingService();
    int requiredProfitMargin();

}
