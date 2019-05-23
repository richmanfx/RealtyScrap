package ru.r5am;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;

class Calculation {

    private static final Logger log = LogManager.getRootLogger();

    static ArrayList<CalculatedResult> calculation(ArrayList<ObjectInfo> allObjectsInfo) {

        ArrayList<CalculatedResult> bigCalculatedResult = new ArrayList<>();

//        for(ObjectInfo info: allObjectsInfo) {
        for(int index = 0; index < allObjectsInfo.size(); index++) {

            CalculatedResult calculatedResult = new CalculatedResult();

            // Порядковый номер
            calculatedResult.orderNumber = Integer.toString(index + 1);

            // Номер извещения
            calculatedResult.notificationNumber = allObjectsInfo.get(index).notificationNumber;

            // Адрес объекта
            calculatedResult.address = allObjectsInfo.get(index).address;

            // Площадь объекта
            float area = Float.parseFloat(allObjectsInfo.get(index).area);
            calculatedResult.area = allObjectsInfo.get(index).area;

            // Выплаты ренты в месяц
            calculatedResult.monthlyRental = allObjectsInfo.get(index).monthlyRental;

            // Срок аренды
            calculatedResult.rentalPeriod = allObjectsInfo.get(index).rentalPeriod;

            // Ссылка на объект на сайте
            calculatedResult.webLink = allObjectsInfo.get(index).webLink;

            // Дата торгов
            calculatedResult.auctionData = allObjectsInfo.get(index).auctionData;

            // Дата окончания подачи заявок
            calculatedResult.closingApplicationsDate = allObjectsInfo.get(index).closingApplicationsDate;

            // Информация о залоге
            calculatedResult.guaranteeAmount = allObjectsInfo.get(index).guaranteeAmount;


            // Стоимость годовой страховки в Альфа-Страховании, рубли
            // зависит от площади в метрах - до 100 кв.м = 4000 рублей
            if(area < 100) {
                calculatedResult.yearInsurance = Float.toString(4000);        // TODO: Вынести в конфиг
//                ObjectInfo object = allObjectsInfo.get(index);
//                calculatedResult.yearInsurance = Float.toString(Float.parseFloat(object.area) * yearAllAreaInsurance);
//                allObjectsInfo.set(index, object);
            } else {
                log.error("Площадь объекта больше чем то, на что расчитана страховка");
            }

            // Стоимость предварительного ремонта всей площади
            int priorRepair = 300;      // TODO: Вынести в конфиг
            calculatedResult.priorRepair = Float.toString(priorRepair * area);

            // Стоимость отопления в месяц
            int monthlyHeating = 23;
            calculatedResult.monthlyHeating = Float.toString(monthlyHeating * area);

            // Обслуживание ЖЭКом в месяц
            int housingOfficeMaintenance = 28;
            calculatedResult.housingOfficeMaintenance = Float.toString(housingOfficeMaintenance * area);

            // Доход от аренды в месяц
            int averageRental = 1292;
            calculatedResult.monthlyProfit = Float.toString(averageRental * area);

            // Доход в год с учётом несдаваемых месяцев
            int profitMonths = 10;
            calculatedResult.yearProfit = Float.toString(averageRental * area * profitMonths);

            // Расходы в месяц
            int accountingService = 2000;       // Бухгалтерское обслуживание в месяц   // TODO: вынести в конфиг
            int contractRegistration = 4000;        // Регистрация договора
            int runningCost = 15000;         // затраты на запуск
            calculatedResult.monthlyCost = Float.toString(
                Float.parseFloat(allObjectsInfo.get(index).monthlyRental) +     // Стоимость аренды в месяц
                monthlyHeating * area +        // Стоимость отопления в месяц
                housingOfficeMaintenance * area +       // Обслуживание ЖЭКом в месяц
                accountingService +         // Бухгалтерское обслуживание в месяц
                (contractRegistration + runningCost) / Float.parseFloat(
                        allObjectsInfo.get(index).rentalPeriod.replace(" лет", "")      // TODO: Здес проверить годы и месяцы!!!
                ) * 12
            );

            // Коэффициент доходности
            calculatedResult.profitMargin = Float.toString(
                    ( averageRental * area * profitMonths - (Float.parseFloat(calculatedResult.monthlyCost) * 12 + Float.parseFloat(calculatedResult.yearInsurance)) )
                     /
                    (contractRegistration + runningCost)
            );

            // Безубыточность сдачи, руб/кв.м. в месяц
            calculatedResult.lossFreeRental = Float.toString(
                    (Float.parseFloat(calculatedResult.monthlyCost) * 12 / profitMonths) / area
            );

            // Выплаты ренты в год
            calculatedResult.yearRental = Float.toString(
                    Float.parseFloat(allObjectsInfo.get(index).monthlyRental) * 12
            );

            // Добавить в результат
            bigCalculatedResult.add(calculatedResult);
        }


        return bigCalculatedResult;
    }

}
