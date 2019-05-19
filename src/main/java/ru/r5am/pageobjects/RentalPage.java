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
        $(By.xpath("//span[text()='Выберите один или несколько типов имущества']")).should(visible);

        // Чекбокс
        String checkBoxXpath = String.format("//td/span[text()='%s']/preceding-sibling::input", propertyType);
        $(By.xpath(checkBoxXpath)).click();

        // Кнопка "Выбрать"
        String buttonXpath = "//ins[text()='Выбрать']";
        $(By.xpath(buttonXpath)).click();


    }

    /**
     * Указать вид договора
     * @param contractType Вид договора
     */
    public void setContractType(String contractType) {

        // Кнопка с картинкой книги
        String contractTypeImgXpath = "//td/label[text()='Вид договора:']/../" +
                                      "following-sibling::td[1]//table//tr/td/a[@title='Выбрать']/img";
        $(By.xpath(contractTypeImgXpath)).click();

        // Чекбокс
        String checkBoxXpath = String.format("//td/span[text()='%s']/preceding-sibling::input", contractType);
        $(By.xpath(checkBoxXpath)).click();

        // Кнопка "Выбрать"
        String buttonXpath = "//ins[text()='Выбрать']";     // TODO: Вынести в отдельный метод
        $(By.xpath(buttonXpath)).click();

    }

    /**
     * Указать страну
     */
    public void setCountry() {

        String labelSelectCountryXpath = "//label[text()='Страна размещения:']";
        $(By.xpath(labelSelectCountryXpath)).click();

        String selectCountryXpath = "//option[@title='РОССИЯ']";
        $(By.xpath(selectCountryXpath)).click();

    }

    /**
     * Указать местоположение имущества
     * @param propertyLocation Субъект РФ
     */
    public void setPropertyLocation(String propertyLocation) {
        String locationImgXpath = "//td/label[text()='Местоположение:']/.." +
                                  "/following-sibling::td[1]//table//tr/td/a[@title='Выбрать']/img";
        sleep(5000);        // AJAX не успевает отрисовать что-то
        $(By.xpath(locationImgXpath)).click();

        // Субъект РФ
        String fieldXpath = "//input[@name='container1:level1']";
        $(By.xpath(fieldXpath)).sendKeys(propertyLocation);

        // Кнопка "Выбрать"
        String buttonXpath = "//ins[text()='Выбрать']";     // TODO: Вынести в отдельный метод
        $(By.xpath(buttonXpath)).click();
    }
}
