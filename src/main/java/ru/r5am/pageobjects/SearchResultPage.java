package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
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

    /**
     * Определить количество найденных лотов
     * @return Количество лотов
     */
    public int getLotsQuantity() {

        // Тупо ждём результатов поиска - AJAX-ом только данные в таблице измненются
        int searchWaitTime = 5;     // Секунды
        sleep(searchWaitTime * 1000);

        String xpath = "//h2/span[contains(text(),'найдено лотов')]";
        String[] strings = $(By.xpath(xpath)).text().split(" ");
        return Integer.parseInt(strings[strings.length - 1]);

    }
}
