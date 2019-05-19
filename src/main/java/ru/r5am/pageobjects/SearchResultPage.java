package ru.r5am.pageobjects;


import java.util.*;
import org.openqa.selenium.By;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Condition.visible;


public class SearchResultPage {

    private static final Logger log = LogManager.getRootLogger();
    private String 	realObjectXpath = "//div[@class='scrollx']/table//tr[contains(@class,'datarow')]";
    private String nextPageXpath = "//a[@title='Перейти на одну страницу вперед']";

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

    /**
     * Вернуть количество объектов на данной странице
     * @return Количество объектов
     */
    public int getLotsOnPage() {
        return $$(By.xpath(realObjectXpath)).size();
    }

    /**
     * Получить список параметров объектов на странице по заданному Xpath
     * @param xpath Xpath параметра
     * @return Список параметров
     */
    private List<String> getObjectsParameter(String xpath) {
        return $$(By.xpath(realObjectXpath + xpath)).texts();
    }

    /**
     * Получить список ивещений объектов на странице
     * @return Список извещений
     */
    public List<String> getNoticeNumbers() {
        return getObjectsParameter("/td[3]/span/span[1]");
    }

    /**
     * Получить список площадей объектов на странице
     * @return Список площадей
     */
    public List<String> getAreas() {
        return getObjectsParameter("/td[3]/span/span[4]");
    }

    /**
     * Получить список стоимости аренды в месяц для объектов на странице
     * @return Стоимость аренды в месяц
     */
    public List<String> getRents() {
        return getObjectsParameter("/td[7]/span");
    }

    /**
     * Получить список сроков аренды для объектов на странице
     * @return Сроки аренды
     */
    public List<String> getRentalPeriods() {
        return getObjectsParameter("/td[6]/span/span[2]");
    }

    /**
     * Получить список ссылок на страницы объектов на странице
     * @return Список ссылок
     */
    public List<String> getLinks() {
        ElementsCollection linksElements = $$(By.xpath(realObjectXpath + "/td[1]//a[@title='Просмотр']"));
        List<String> webLinks = new ArrayList<>();

        for(SelenideElement link: linksElements) {
            webLinks.add(link.getAttribute("href"));
        }
        return webLinks;
    }

    /**
     * Проверить существует ли следующая страница
     * @return true - страница существует, иначе - false
     */
    public boolean existNextPage() {
        boolean result = false;
        if($$(By.xpath(nextPageXpath)).size() > 0) {
            result = true;
        }
        return result;
    }

    /**
     * Перейти на следующую страницу - пагинация
     */
    public void goToNextPage() {

        $(By.xpath(nextPageXpath)).click();
        sleep(3000);        // TODO: Возможно время можно сократить
    }
}
