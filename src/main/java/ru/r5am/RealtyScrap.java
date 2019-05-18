package ru.r5am;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.open;

import static ru.r5am.Scraping.scrap;
import static ru.r5am.SelenideSetUp.appConfig;

public class RealtyScrap {

    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) {

        SelenideSetUp selenideSetUp = new SelenideSetUp();

        selenideSetUp.selenideStart();
        log.debug("Selenide сконфигурирован");

        open(appConfig.scrapUrl());
        log.debug("Сайт открыт");

        log.debug("Начать скрапинг");
        scrap();

        log.debug("");
    }

}
