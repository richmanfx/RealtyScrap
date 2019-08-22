package ru.r5am;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;

import static ru.r5am.Calculation.calculation;
import static ru.r5am.Scraping.scrap;
import static ru.r5am.SelenideSetUp.appConfig;


public class RealtyScrap {

    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {

        SelenideSetUp selenideSetUp = new SelenideSetUp();
        HtmlReport report = new HtmlReport();

        selenideSetUp.selenideStart();
        log.debug("Selenide is configured");

        open(appConfig.scrapUrl());
        log.debug("Site is open");

        log.debug("Scraping started");
        ArrayList<ObjectInfo> allObjectsInfo = scrap();

        log.debug("Calculate all parameters for each object");
        ArrayList<CalculatedResult> calculatedResult = calculation(allObjectsInfo);

        log.debug("Output the results of calculations");
        report.create(calculatedResult);

    }

}
