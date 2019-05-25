package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Condition.visible;


public class RentalPage {

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
     * @param country Название страны
     */
    public void setCountry(String country) {
        String labelSelectCountryXpath = "//label[text()='Страна размещения:']";
        $(By.xpath(labelSelectCountryXpath)).click();

        String selectCountryXpath = String.format("//option[@title='%s']", country);
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

    /**
     * Указать диапазон площади объекта
     * @param minArea Минимальная площадь объекта
     * @param maxArea Максимальная площадь объекта
     */
    public void setObjectAreaRange(int minArea, int maxArea) {
        String minFieldXpath = "//input[@name='extended:areaMeters:stringAreaMetersFrom']";
        $(By.xpath(minFieldXpath)).sendKeys(Integer.toString(minArea));

        String maxFieldXpath = "//input[@name='extended:areaMeters:stringAreaMetersTo']";
        $(By.xpath(maxFieldXpath)).sendKeys(Integer.toString(maxArea));
    }

    /**
     * Указать минимальный срок аренды
     * @param minRentalPeriod Срок аренды в годах
     */
    public void setRentalPeriod(int minRentalPeriod) {
        String fieldXpath = "//input[@name='extended:propertyExtended:stringRentFrom']";
        $(By.xpath(fieldXpath)).sendKeys(Integer.toString(minRentalPeriod * 12));       // В месяцах
    }

    /**
     * Кликнуть на кнопке поиска
     */
    public void searchButtonClick() {
        String buttonXpath = "//ins[@id='lot_search']";
        $(By.xpath(buttonXpath)).click();
    }

}
