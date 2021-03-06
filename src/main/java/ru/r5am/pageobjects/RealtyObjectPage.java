package ru.r5am.pageobjects;


import org.openqa.selenium.By;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.$;


public class RealtyObjectPage {

    private static final Logger log = LogManager.getRootLogger();

    /**
     * Вернуть информацию о залоге
     * @return Инфо про залог
     */
    public String getDeposit() {
        String depositXpath = "//label[contains(text(),'Описание обременения')]/../../td/span";
        String deposit = $(By.xpath(depositXpath)).text();
        if(deposit.equals("")) {
            depositXpath = "//label[contains(text(),'Размер задатка')]/../../td//table//span";
            deposit = $(By.xpath(depositXpath)).text();
        }
        return deposit;
    }

    /**
     * Вернуть информацию об адресе
     * @return Инфо про адрес
     */
    public String getAddress() {
        String addressXpath = "//label[contains(text(),'Детальное местоположение имущества')]/../../td/span";
        return $(By.xpath(addressXpath)).text();
    }

    /**
     * Перейти на закладку "Общие"
     */
    public void toGeneralTab() {
        String tabXpath = "//span[text()='Общие']";
        $(By.xpath(tabXpath)).click();
    }

    /**
     * Вернуть дату проведения торгов
     * @return Дата торгов
     */
    public String getAuctionData() {
        String auctionDateXpath = "//label[contains(text(),'Дата и время проведения аукциона')]/../../td/span";
        return $(By.xpath(auctionDateXpath)).text();
    }

    /**
     * Дата окончания подачи заявок
     * @return Дата
     */
    public String getClosingApplicationsDate() {
        String closingApplicationsDateXpath = "//label[contains(text(),'Дата окончания подачи заявок')]/../../td/span";
        return $(By.xpath(closingApplicationsDateXpath)).text();
    }

}
