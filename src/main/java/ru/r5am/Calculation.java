package ru.r5am;

import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Calculation {

    private static final Logger log = LogManager.getRootLogger();

    static ArrayList<CalculatedResult> calculation(ArrayList<ObjectInfo> allObjectsInfo) {

        ArrayList<CalculatedResult> bigCalculatedResult = new ArrayList<>();

//        for(ObjectInfo info: allObjectsInfo) {
        for (ObjectInfo objectInfo : allObjectsInfo) {

            CalculatedResult calculatedResult = new CalculatedResult();

            // Номер извещения
            calculatedResult.notificationNumber = objectInfo.notificationNumber;

            // Адрес объекта
            calculatedResult.address = objectInfo.address;

            // Площадь объекта
            float area = Float.parseFloat(objectInfo.area);
            calculatedResult.area = objectInfo.area;

            // Выплаты ренты в месяц
            calculatedResult.monthlyRental = objectInfo.monthlyRental;

            // Срок аренды
            calculatedResult.rentalPeriod = objectInfo.rentalPeriod;

            // Ссылка на объект на сайте
            calculatedResult.webLink = objectInfo.webLink;

            // Дата торгов
            calculatedResult.auctionData = objectInfo.auctionData;

            // Дата окончания подачи заявок
            calculatedResult.closingApplicationsDate = objectInfo.closingApplicationsDate;

            // Информация о залоге
            calculatedResult.guaranteeAmount = objectInfo.guaranteeAmount;


            // Стоимость годовой страховки в Альфа-Страховании, рубли
            // зависит от площади в метрах - до 100 кв.м = 4000 рублей
            if (area < 100) {
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
                    Float.parseFloat(objectInfo.monthlyRental) +     // Стоимость аренды в месяц
                            monthlyHeating * area +        // Стоимость отопления в месяц
                            housingOfficeMaintenance * area +       // Обслуживание ЖЭКом в месяц
                            accountingService +         // Бухгалтерское обслуживание в месяц
                            (contractRegistration + runningCost) / Float.parseFloat(
                                    objectInfo.rentalPeriod.replace(" лет", "")      // TODO: Здес проверить годы и месяцы!!!
                            ) * 12
            );

            // Коэффициент доходности
            calculatedResult.profitMargin = Integer.toString(
                    Math.round(
                            (averageRental * area * profitMonths - (Float.parseFloat(calculatedResult.monthlyCost) * 12 + Float.parseFloat(calculatedResult.yearInsurance)))
                                    /
                                    (contractRegistration + runningCost)
                    )
            );

            // Безубыточность сдачи, руб/кв.м. в месяц
            calculatedResult.lossFreeRental = Float.toString(
                    (Float.parseFloat(calculatedResult.monthlyCost) * 12 / profitMonths) / area
            );

            // Выплаты ренты в год
            calculatedResult.yearRental = Float.toString(
                    Float.parseFloat(objectInfo.monthlyRental) * 12
            );


            // Добавить в результат
            bigCalculatedResult.add(calculatedResult);
        }


        // TODO: сделать switch для разных полей для сортировки
        // Отсортировать большой словарь по значению, указанному в конфиге
        bigCalculatedResult.sort(CalculatedResult.ProfitMarginComparator);


        // Проставить порядковый номер в первом столбце  TODO:
//        bigCalculatedResult orderNumber = Integer.toString(index + 1);



        return bigCalculatedResult;
    }

}
