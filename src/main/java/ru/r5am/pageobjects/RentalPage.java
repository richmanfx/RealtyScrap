package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Condition.visible;


public class RentalPage {

    private static final Logger log = LogManager.getRootLogger();

    /**
     * Войти в расширенный поиск
     */
    public void comeInExtSearch() {
        String extSearchButtonXpath = "//ins[@id='ext_search']";
        $(By.xpath(extSearchButtonXpath)).click();
    }

    /**
     * Выбрать тип торгов
     */
    public void setTradesType() {
        String actionTypeLinkXpath = "//li[text()='В процессе подачи заявок']";
        $(By.xpath(actionTypeLinkXpath)).click();
    }

    /**
     * Указать тип имущества
     * @param propertyType Тип имущества
     */
    public void setAuctionType(String propertyType) {

        // Кнопка с картинкой книги
        String propertyTypeImgXpath =
                "//td/label[text()='Тип имущества:']/../following-sibling::td[1]//table//tr/td/a[@title='Выбрать']/img";

        sleep(5000);        // AJAX не успевает отрисовать что-то
        $(By.xpath(propertyTypeImgXpath)).click();
        log.info("Книжка кликнута");

        $(By.xpath("//span[text()='Выберите один или несколько типов имущества']")).should(visible);

        // Чекбокс
        String checkBoxXpath = String.format("//td/span[text()='%s']/preceding-sibling::input", propertyType);
        $(By.xpath(checkBoxXpath)).click();

    }
}
