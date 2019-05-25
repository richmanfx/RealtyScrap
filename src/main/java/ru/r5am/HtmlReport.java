package ru.r5am;

import java.util.List;
import java.util.ArrayList;

class HtmlReport {

    /**
     * Создание HTML отчёта
     * @param result Результат расчётов всех параметров каждого объекта
     */
    void create(ArrayList<CalculatedResult> result) {

        // Подготовить заголовки столбцов
        List<String> titles = getTableTitles();

        // Генерация отчёта

    }

    /**
     * Подготовить заголовки столбцов
     * @return Массив заголовков для HTML таблицы
     */
    private List<String> getTableTitles() {
        List<String> titles = new ArrayList<>();

        titles.add("N");
        titles.add("Номер извещения");
        titles.add("Коэффициент доходности");
        titles.add("Адрес");
        titles.add("Площадь, кв.м");
        titles.add("Дата торгов");
        titles.add("Дата окончания подачи заявок");
        titles.add("Сумма залога");
        titles.add("Безубыточная сдача, руб/кв.м. в месяц");

        titles.add("Доход в месяц, рублей");
        titles.add("Расходы в месяц, рублей");
        titles.add("Выплата ренты в месяц, рублей");
        titles.add("Срок аренды, месяцев");
        titles.add("Стоимость отопления в месяц, рублей");
        titles.add("Обслуживание ЖЭКом в месяц, рублей");

        titles.add("Доход в год, рублей");
        titles.add("Выплата ренты в год, рублей");
        titles.add("Страховка за год, рублей");
        titles.add("Предварительный ремонт, рублей");


        return titles;
    }
}
