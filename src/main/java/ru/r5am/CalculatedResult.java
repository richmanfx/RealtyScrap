package ru.r5am;

import lombok.Getter;
import lombok.Setter;
import java.util.Comparator;

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
    static Comparator<CalculatedResult> ProfitMarginComparator = new Comparator<>() {
        @Override
        public int compare(CalculatedResult result1, CalculatedResult result2) {
            return (Math.round(Float.parseFloat(result2.profitMargin)) -
                    Math.round(Float.parseFloat(result1.profitMargin)));
        }
    };

}
