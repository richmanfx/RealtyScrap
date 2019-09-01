package ru.r5am;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCalculation {

    private static final Logger log = LogManager.getRootLogger();

    @DataProvider
    public static Object[][] testData() {

        ArrayList<ObjectInfo> testData = new ArrayList<>();
        ObjectInfo objectInfo = new ObjectInfo();

        objectInfo.notificationNumber = "123456/1234567/12";
        objectInfo.address = "г. Москва, ул. Тестовая, д. 7";
        objectInfo.area = "72.2";
        objectInfo.monthlyRental = "12345";
        objectInfo.rentalPeriod = "10 лет";
        objectInfo.webLink = "https://torgi.gov.ru";
        objectInfo.auctionData = "26.09.2019 13:00";
        objectInfo.closingApplicationsDate = "20.09.2019";
        objectInfo.guaranteeAmount = "Задаток для участия в аукционе установлен в размере 12 345 " +
                                     "(Двенадцать тысяч триста сорок пять) рублей 00 копеек.";

        testData.add(objectInfo);

        List<CalculatedResult> expectedResult = new ArrayList<>();
        CalculatedResult calculatedResult = new CalculatedResult();

        // Исходные параметры в результате
        calculatedResult.notificationNumber = "123456/1234567/12";
        log.info("Номер извещения (notificationNumber): '{}'", calculatedResult.notificationNumber);

        calculatedResult.address = "г. Москва, ул. Тестовая, д. 7";
        log.info("Адрес (address): '{}'", calculatedResult.address);

        calculatedResult.area = "72.2";
        log.info("Площадь (area): '{}'", calculatedResult.area);

        calculatedResult.monthlyRental = "12 345";
        log.info("Стоимость аренды в месяц (monthlyRental): '{}'", calculatedResult.monthlyRental);

        calculatedResult.rentalPeriod = "10 лет";
        log.info("Срок аренды (rentalPeriod): '{}'", calculatedResult.rentalPeriod);

        calculatedResult.webLink = "https://torgi.gov.ru";
        log.info("Ссылка для просмотра (webLink): '{}'", calculatedResult.webLink);

        calculatedResult.auctionData = "26.09.2019 13:00";
        log.info("Дата проведения аукциона (auctionData): '{}'", calculatedResult.auctionData);

        calculatedResult.closingApplicationsDate = "20.09.2019";
        log.info(
                "Дата окончания подачи заявок (closingApplicationsDate): '{}'",
                calculatedResult.closingApplicationsDate);

        calculatedResult.guaranteeAmount = "Задаток для участия в аукционе установлен в размере 12 345 " +
                                           "(Двенадцать тысяч триста сорок пять) рублей 00 копеек.";
        log.info("Информация про залог (guaranteeAmount): '{}'", calculatedResult.guaranteeAmount);


        // Рассчитанные параметры объекта в результате
        calculatedResult.orderNumber = "1";
        log.info("Порядковый номер объекта (orderNumber): '{}'", calculatedResult.orderNumber);

        calculatedResult.profitMargin = "23";
        log.info("Коэффициент доходности (profitMargin): '{}'", calculatedResult.profitMargin);

        calculatedResult.lossFreeRental = " 679";         // TODO: Ведущий пробел в фактическом результате???
        log.info("Безубыточная сдача, руб/кв.м. в месяц (lossFreeRental): '{}'", calculatedResult.lossFreeRental);

        calculatedResult.yearRental = "148 140";
        log.info("Выплаты ренты в год (yearRental): '{}'", calculatedResult.yearRental);

        calculatedResult.yearInsurance = "4 000";
        log.info("Страховка за год (yearInsurance): '{}'", calculatedResult.yearInsurance);

        calculatedResult.monthlyCost = "40 827";
        log.info("Расходы в месяц (monthlyCost): '{}'", calculatedResult.monthlyCost);

        calculatedResult.monthlyHeating = "1 661";
        log.info("Стоимость отопления в месяц (monthlyHeating): '{}'", calculatedResult.monthlyHeating);

        calculatedResult.housingOfficeMaintenance = "2 022";
        log.info(
                "Обслуживание ЖЭКом в месяц (housingOfficeMaintenance): '{}'",
                calculatedResult.housingOfficeMaintenance);

        calculatedResult.monthlyProfit = "93 282";
        log.info("Доход в месяц (monthlyProfit): '{}'", calculatedResult.monthlyProfit);

        calculatedResult.yearProfit = "932 824";
        log.info("Доход в год (yearProfit): '{}'", calculatedResult.yearProfit);

        calculatedResult.priorRepair = "21 660";
        log.info("Предварительный ремонт (priorRepair): '{}'", calculatedResult.priorRepair);

        expectedResult.add(calculatedResult);

        return new Object[][]{
                {testData, expectedResult}
        };
    }

    @Test(dataProvider = "testData")
    public void testCalculation(List<ObjectInfo> allObjectsInfo, List<CalculatedResult> expectedResult) {

        // Фактический результат
        List<CalculatedResult> actualResult = Calculation.calculation((ArrayList<ObjectInfo>) allObjectsInfo);

        // Проверка
        Assert.assertEquals(actualResult.get(0), expectedResult.get(0));
    }
}
