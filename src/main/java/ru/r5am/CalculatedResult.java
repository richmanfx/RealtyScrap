package ru.r5am;

import lombok.Getter;
import lombok.Setter;
import java.util.Comparator;
import java.util.Objects;

class CalculatedResult extends ObjectInfo {

    // Инфо на основе расчётов
    @Getter @Setter     String  orderNumber;                // Порядковый номер объекта
    @Getter @Setter     String  profitMargin;               // Коэффициент доходности
    @Getter @Setter     String  lossFreeRental;             // Безубыточная сдача, руб/кв.м. в месяц
    @Getter @Setter     String  yearRental;                 // Выплаты ренты в год
    @Getter @Setter     String  yearInsurance;              // Страховка за год
    @Getter @Setter     String  monthlyCost;                // Расходы в месяц
    @Getter @Setter     String  monthlyHeating;             // Стоимость отопления в месяц
    @Getter @Setter     String  housingOfficeMaintenance;   // Обслуживание ЖЭКом в месяц
    @Getter @Setter     String  monthlyProfit;              // Доход в месяц
    @Getter @Setter     String  yearProfit;                 // Доход в год
    @Getter @Setter     String  priorRepair;                // Предварительный ремонт


    // Компаратор сортирует по коэффициенту доходности "profitMargin"
    static Comparator<CalculatedResult> profitMarginComparator =
            (result1, result2) -> (
                    Math.round(Float.parseFloat(result2.profitMargin)) -
                    Math.round(Float.parseFloat(result1.profitMargin))
            );

    @Override
    public int hashCode() {
        return Objects.hash(
                orderNumber, profitMargin, lossFreeRental, yearRental, yearInsurance, monthlyCost,
                monthlyHeating, housingOfficeMaintenance, monthlyProfit, yearProfit, priorRepair);
    }

    @Override
    public boolean equals(Object obj) {

        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(!(obj instanceof CalculatedResult))
            return false;

        CalculatedResult object = (CalculatedResult) obj;

        return orderNumber.equals(object.orderNumber) &&
               profitMargin.equals(object.profitMargin) &&
               lossFreeRental.equals(object.lossFreeRental) &&
               yearRental.equals(object.yearRental) &&
               yearInsurance.equals(object.yearInsurance) &&
               monthlyCost.equals(object.monthlyCost) &&
               monthlyHeating.equals(object.monthlyHeating) &&
               housingOfficeMaintenance.equals(object.housingOfficeMaintenance) &&
               monthlyProfit.equals(object.monthlyProfit) &&
               yearProfit.equals(object.yearProfit) &&
               priorRepair.equals(object.priorRepair);
    }

}
