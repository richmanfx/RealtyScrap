package ru.r5am;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

class HtmlReport {

    private static final Logger log = LogManager.getRootLogger();
    private static AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    /**
     * Создание HTML отчёта
     * @param result Результат расчётов всех параметров каждого объекта
     */
    void create(ArrayList<CalculatedResult> result) throws IOException {

        // Подготовить заголовки столбцов
        List<String> titles = getTableTitles();

        // Генерация отчёта
        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = Maps.newHashMap();

        context.put("titles", titles);
//        context.a("realty", result.get(0));
//        context.put("realty", new Integer[] { 23, 45, 56, 78 });

        List<List<String>> ccc = new ArrayList<>();

        List<String> aaa = new ArrayList<>();
        aaa.add("a-Первый");
        aaa.add("a-Второй");
        aaa.add("a-Третий");
        aaa.add("a-Четвёртый");
        aaa.add("a-Пятый");

        ccc.add(aaa);

        List<String> bbb = new ArrayList<>();
        aaa.add("b-Первый");
        aaa.add("b-Второй");
        aaa.add("b-Третий");
        aaa.add("b-Четвёртый");
        aaa.add("b-Пятый");

        ccc.add(bbb);


        context.put("realty", ccc);

        URL url = getClass().getResource(File.separator +"templates" + File.separator + "result-torgi-gov-ru.html");
        String template = IOUtils.toString(url, Charsets.UTF_8);
        log.info("Темплейт:\n{}", template);

        String renderedTemplate = jinjava.render(template, context);

        // Сохранить в файл
        toFileSave(renderedTemplate);

    }

    /**
     * Сохранить отчёт в файл
     * @param renderedTemplate Отрендеренный отчёт
     */
    private void toFileSave(String renderedTemplate) throws IOException {

        BufferedWriter bufferedWriter;

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());

        File reportFile = new File(appConfig.reportDir() + File.separator + "realty_report-" + timeStamp + ".html");
        if (!reportFile.exists()) {
            boolean flag = reportFile.createNewFile();
            if(flag) {
                log.info("Файл '{}' созадан успешно", reportFile.getName());
            } else {
                log.error("Файл '{}' создать не удалось!", reportFile.getName());
            }
        }

        FileWriter writer = new FileWriter(reportFile);
        bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(renderedTemplate);
        bufferedWriter.close();
        writer.close();

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
