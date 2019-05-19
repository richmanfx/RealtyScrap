package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;


public class SearchResultPage {

    private static final Logger log = LogManager.getRootLogger();

    // Проверить отобразились ли лоты по объектам после поиска
    public boolean isObjectsShow() {

        boolean result = true;

        String checkXpath = "//h2/span[contains(text(),'найдено лотов')]";
        try {
            $(By.xpath(checkXpath)).should(visible);
            log.info("Лоты отобразились - удачный поиск");
        } catch (Exception ex) {    // TODO: Сузить эксцептион
            log.info("Лоты НЕ отобразились - объекты не нашлись");
            result = false;
        }

        return result;

    }
}
