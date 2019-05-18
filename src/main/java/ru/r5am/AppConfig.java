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
}
