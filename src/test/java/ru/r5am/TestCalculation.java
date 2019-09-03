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
        objectInfo.area = "31.5";
        objectInfo.monthlyRental = "10329.38";
        objectInfo.rentalPeriod = "10 лет";
        objectInfo.webLink = "https://torgi.gov.ru";
        objectInfo.auctionData = "26.09.2019 13:00";
        objectInfo.closingApplicationsDate = "20.09.2019";
        objectInfo.guaranteeAmount = "Задаток для участия в аукционе установлен в размере 12 345 " +
                                     "(Двенадцать тысяч триста сорок пять) рублей 00 копеек.";

        testData.add(objectInfo);

        List<CalculatedResult> expectedResult = new ArrayList<>();
        CalculatedResult calculatedResult = new CalculatedResult();

        // Ожидаемые исходные параметры в результате
        calculatedResult.notificationNumber = "123456/1234567/12";
        log.info("Номер извещения (notificationNumber): '{}'", calculatedResult.notificationNumber);

        calculatedResult.address = "г. Москва, ул. Тестовая, д. 7";
        log.info("Адрес (address): '{}'", calculatedResult.address);

        calculatedResult.area = "31.5";
        log.info("Площадь (area): '{}'", calculatedResult.area);

        calculatedResult.monthlyRental = "10 329.38";
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


        // Ожидаемые рассчитанные параметры объекта в результате
        calculatedResult.orderNumber = "1";
        log.info("Порядковый номер объекта (orderNumber): '{}'", calculatedResult.orderNumber);

        calculatedResult.profitMargin = "23";
        log.info("Коэффициент доходности (profitMargin): '{}'", calculatedResult.profitMargin);

        calculatedResult.lossFreeRental = " 679";         // TODO: Ведущий пробел в фактическом результате???
        log.info("Безубыточная сдача, руб/кв.м. в месяц (lossFreeRental): '{}'", calculatedResult.lossFreeRental);

        calculatedResult.yearRental = "123 953";
        log.info("Выплаты ренты в год (yearRental): '{}'", calculatedResult.yearRental);

        calculatedResult.yearInsurance = "11 340";
        log.info("Страховка за год (yearInsurance): '{}'", calculatedResult.yearInsurance);

        calculatedResult.monthlyCost = "40 827";
        log.info("Расходы в месяц (monthlyCost): '{}'", calculatedResult.monthlyCost);

        calculatedResult.monthlyHeating = " 725";
        log.info("Стоимость отопления в месяц (monthlyHeating): '{}'", calculatedResult.monthlyHeating);

        calculatedResult.housingOfficeMaintenance = " 851";
        log.info(
                "Обслуживание ЖЭКом в месяц (housingOfficeMaintenance): '{}'",
                calculatedResult.housingOfficeMaintenance);

        calculatedResult.monthlyProfit = "31 500";
        log.info("Доход в месяц (monthlyProfit): '{}'", calculatedResult.monthlyProfit);

        calculatedResult.yearProfit = "315 000";
        log.info("Доход в год (yearProfit): '{}'", calculatedResult.yearProfit);

        calculatedResult.priorRepair = " 0";
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
        CalculatedResult real = actualResult.get(0);

        // Ожидаемый результат
        CalculatedResult expected = expectedResult.get(0);



        // Проверки
        boolean assertFlag = true;

        if (!real.orderNumber.equals(expected.orderNumber)) {
            log.error("Порядковый номер объекта не равен ожидаемому. Реальный: '{}'. Ожидаемый '{}'.",
                    real.orderNumber, expected.orderNumber);
            assertFlag = false;
        }

        if (!real.profitMargin.equals(expected.profitMargin)) {
            log.error("Коэффициент доходности не равен ожидаемому. Реальный: '{}'. Ожидаемый '{}'.",
                       real.profitMargin, expected.profitMargin);
            assertFlag = false;
        }

        if (!real.lossFreeRental.equals(expected.lossFreeRental)) {
            log.error("Безубыточная сдача в месяц не равна ожидаемой. Реальная: '{}'. Ожидаемая '{}'.",
                    real.lossFreeRental, expected.lossFreeRental);
            assertFlag = false;
        }

        if (!real.yearRental.equals(expected.yearRental)) {
            log.error("Выплата ренты в год не равна ожидаемой. Реальная: '{}'. Ожидаемая '{}'.",
                    real.yearRental, expected.yearRental);
            assertFlag = false;
        }

        if (!real.yearInsurance.equals(expected.yearInsurance)) {
            log.error("Страховка за год не равна ожидаемой. Реальная: '{}'. Ожидаемая '{}'.",
                    real.yearInsurance, expected.yearInsurance);
            assertFlag = false;
        }

        if (!real.monthlyCost.equals(expected.monthlyCost)) {
            log.error("Расходы в месяц не равны ожидаемым. Реальные: '{}'. Ожидаемые '{}'.",
                    real.monthlyCost, expected.monthlyCost);
            assertFlag = false;
        }

        if (!real.monthlyHeating.equals(expected.monthlyHeating)) {
            log.error("Стоимость отопления в месяц не равна ожидаемой. Реальная: '{}'. Ожидаемая '{}'.",
                    real.monthlyHeating, expected.monthlyHeating);
            assertFlag = false;
        }

        if (!real.housingOfficeMaintenance.equals(expected.housingOfficeMaintenance)) {
            log.error("Обслуживание ЖЭКом в месяц не равно ожидаемому. Реальное: '{}'. Ожидаемое '{}'.",
                    real.housingOfficeMaintenance, expected.housingOfficeMaintenance);
            assertFlag = false;
        }

        if (!real.monthlyProfit.equals(expected.monthlyProfit)) {
            log.error("Доход в месяц не равен ожидаемому. Реальный: '{}'. Ожидаемый '{}'.",
                    real.monthlyProfit, expected.monthlyProfit);
            assertFlag = false;
        }

        if (!real.yearProfit.equals(expected.yearProfit)) {
            log.error("Доход в год не равен ожидаемому. Реальный: '{}'. Ожидаемый '{}'.",
                    real.yearProfit, expected.yearProfit);
            assertFlag = false;
        }

        if (!real.priorRepair.equals(expected.priorRepair)) {
            log.error("Затраты на предварительный ремонт не равны ожидаемым. Реальные: '{}'. Ожидаемые '{}'.",
                    real.priorRepair, expected.priorRepair);
            assertFlag = false;
        }

        assert assertFlag;

    }
}
