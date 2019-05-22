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

            // Площадь объекта
            Float area = Float.parseFloat(allObjectsInfo.get(index).area);

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




            // Добавить в результат
            bigCalculatedResult.add(calculatedResult);
        }


        return bigCalculatedResult;
    }

}
