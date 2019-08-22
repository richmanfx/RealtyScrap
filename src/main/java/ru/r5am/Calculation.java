package ru.r5am;

import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.StringUtils;

class Calculation {

    private static final Logger log = LogManager.getRootLogger();
    private static AppConfig appConfig = ConfigFactory.create(AppConfig.class);


    static ArrayList<CalculatedResult> calculation(ArrayList<ObjectInfo> allObjectsInfo) {

        ArrayList<CalculatedResult> bigCalculatedResult = new ArrayList<>();

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
            calculatedResult.monthlyRental = spaceInsert(objectInfo.monthlyRental);

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
                calculatedResult.yearInsurance =
                        spaceInsert(Integer.toString(Math.round(appConfig.yearlyInsurance())));
            } else {
                log.error("The area of the object is more than what the insurance is designed for");
            }

            // Стоимость предварительного ремонта всей площади
            calculatedResult.priorRepair =
                    spaceInsert(Integer.toString(Math.round(appConfig.priorRepair() * area)));

            // Стоимость отопления в месяц
            calculatedResult.monthlyHeating =
                    spaceInsert(Integer.toString(Math.round(appConfig.monthlyHeating() * area)));

            // Обслуживание ЖЭКом в месяц
            calculatedResult.housingOfficeMaintenance =
                    spaceInsert(Integer.toString(Math.round(appConfig.housingOfficeMaintenance() * area)));

            // Доход от аренды в месяц
            calculatedResult.monthlyProfit =
                    spaceInsert(Integer.toString(Math.round(appConfig.averageRental() * area)));

            // Доход в год с учётом несдаваемых месяцев
            calculatedResult.yearProfit =
                    spaceInsert(Integer.toString(Math.round(
                            appConfig.averageRental() * area * appConfig.profitMonths())));

            // Расходы в месяц
            calculatedResult.monthlyCost =
                    spaceInsert(Integer.toString(Math.round(
                        Float.parseFloat(objectInfo.monthlyRental) +     // Стоимость аренды в месяц
                            appConfig.monthlyHeating() * area +        // Стоимость отопления в месяц
                            appConfig.housingOfficeMaintenance() * area +       // Обслуживание ЖЭКом в месяц
                            appConfig.accountingService() +         // Бухгалтерское обслуживание в месяц
                            (appConfig.contractRegistration() + appConfig.runningCost()) / Float.parseFloat(
                                    objectInfo.rentalPeriod.replace(" лет", "")      // TODO: Здес проверить годы и месяцы!!!
                            ) * 12
                    )));

            // Коэффициент доходности
            calculatedResult.profitMargin = Integer.toString(
                    Math.round(
                            (appConfig.averageRental() * area * appConfig.profitMonths() -
                                    (Float.parseFloat(calculatedResult.monthlyCost.replace(" ", "")) * 12 +
                                     Float.parseFloat(calculatedResult.yearInsurance.replace(" ", ""))))
                                    /
                                    (appConfig.contractRegistration() + appConfig.runningCost())
                    )
            );

            // Безубыточность сдачи, руб/кв.м. в месяц
            calculatedResult.lossFreeRental =
                    spaceInsert(Integer.toString(Math.round(
                        (
                         Float.parseFloat(calculatedResult.monthlyCost.replace(" ", "")) * 12 / appConfig.profitMonths()
                        )
                         /
                        area
                    )));

            // Выплаты ренты в год
            calculatedResult.yearRental = spaceInsert(Integer.toString(Math.round(
                    Float.parseFloat(objectInfo.monthlyRental) * 12)));


            // Добавить в результат
            bigCalculatedResult.add(calculatedResult);
        }

        // Отсортировать большой словарь по значению, указанному в конфиге
        switch (appConfig.sortFieldName()) {
            case "bar":
//                bigCalculatedResult.sort(CalculatedResult.Bar);
                break;
            case "foo":
//                bigCalculatedResult.sort(CalculatedResult.Foo);
                break;
            default:
                bigCalculatedResult.sort(CalculatedResult.ProfitMarginComparator);
        }

        // Проставить порядковый номер в первом столбце
        for(int i = 0; i < bigCalculatedResult.size(); i++) {
            bigCalculatedResult.get(i).orderNumber = Integer.toString(i + 1);
        }

        return bigCalculatedResult;
    }

    /**
     * Вставляет в строку пробел поле третьего сивола с конца.
     * Для визуального отделения единиц рублей от тысяч рублей.
     * @param str Исходная строка
     * @return Строка с пробелом
     */
    private static String spaceInsert(String str) {
        int position = str.length()-3;
        return StringUtils.overlay(str," ", position, position);
    }

}
