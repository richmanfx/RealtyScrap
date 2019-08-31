package ru.r5am;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCalculation {


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
        calculatedResult.notificationNumber = "123456/1234567/12";
        calculatedResult.address = "г. Москва, ул. Тестовая, д. 7";
        calculatedResult.area = "72.2";
        calculatedResult.monthlyRental = "12 345";
        calculatedResult.rentalPeriod = "10 лет";
        calculatedResult.webLink = "https://torgi.gov.ru";
        calculatedResult.auctionData = "26.09.2019 13:00";
        calculatedResult.closingApplicationsDate = "20.09.2019";
        calculatedResult.guaranteeAmount = "Задаток для участия в аукционе установлен в размере 12 345 " +
                                           "(Двенадцать тысяч триста сорок пять) рублей 00 копеек.";

        calculatedResult.orderNumber = "1";
        calculatedResult.profitMargin = "23";
        calculatedResult.lossFreeRental = " 679";         // TODO: Ведущий пробел в фактическом результате???
        calculatedResult.yearRental = "148 140";
        calculatedResult.yearInsurance = "4 000";
        calculatedResult.monthlyCost = "40 827";
        calculatedResult.monthlyHeating = "1 661";
        calculatedResult.housingOfficeMaintenance = "2 022";
        calculatedResult.monthlyProfit = "93 282";
        calculatedResult.yearProfit = "932 824";
        calculatedResult.priorRepair = "21 660";

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
