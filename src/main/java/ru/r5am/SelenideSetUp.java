package ru.r5am;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;

class SelenideSetUp {

    SelenideSetUp() {
    }

    static AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    static void selenideStart() {
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 1000 * 7L;  // из секунд в мс
        Configuration.browser = appConfig.selenideBrowser();
        Configuration.browserSize = appConfig.browserSize();
    }

}
