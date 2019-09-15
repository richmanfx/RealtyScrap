package ru.r5am;


import java.io.*;
import java.util.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.hubspot.jinjava.Jinjava;
import org.apache.commons.io.IOUtils;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.Logger;
import com.google.common.base.Charsets;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;


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

        // Подготовить параметры из конфига
        Map<String, String> settings = getReportSettings();

        // Подготовить результаты скрапинга для вывода в отчёт
        List<Map<String, String>> objects = getReportObjects(result);


        // Генерация отчёта
        Jinjava jinjava = new Jinjava();
        Map<String, Object> context = Maps.newHashMap();

        context.put("titles", titles);
        context.put("settings", settings);
        context.put("objects", objects);

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

        URL url = getClass().getResource( "/templates/result-torgi-gov-ru.html");
        log.info("Template uri: {}", url);
        String template = IOUtils.toString(url, Charsets.UTF_8);
        String renderedTemplate = jinjava.render(template, context);

        // Сохранить в файл
        toFileSave(renderedTemplate);

    }

    /**
     * Сохранить отчёт в файл
     * @param renderedTemplate Отрендеренный отчёт
     */
    private void toFileSave(String renderedTemplate) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());

        String pathName = appConfig.reportDir() + File.separator + "realty_report-" + timeStamp + ".html";
        File reportFile = new File(pathName);
        if (!reportFile.exists()) {
            boolean createFileFlag = false;
            try {
                createFileFlag = reportFile.createNewFile();
            } catch (IOException ex) {
                log.error("Create file error '{}'", pathName);
            }
            if(createFileFlag) {
                log.info("File '{}' created successfully", reportFile.getName());
            }
        }

        try (
                FileWriter writer = new FileWriter(reportFile);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)
            ) {
                bufferedWriter.write(renderedTemplate);
        } catch (IOException ex) {
            log.error("Error writing report to file: {}", ex.toString());
        }



    }

    /**
     * Подготовить результат скрапинга для отчёта HTML
     * @param result Резудьтат скрапинга
     * @return Коллекция результатов
     */

    private List<Map<String, String>> getReportObjects(ArrayList<CalculatedResult> result) {

        List<Map<String, String>> allObjects = new ArrayList<>();

        for(CalculatedResult oneObjectResult: result) {

            Map<String, String> object = new HashMap<>();

            object.put("orderNumber", oneObjectResult.orderNumber);
            object.put("webLink", oneObjectResult.webLink);
            object.put("notificationNumber", oneObjectResult.notificationNumber);
            object.put("profitMargin", oneObjectResult.profitMargin);

            object.put("address", oneObjectResult.address);
            object.put("area", oneObjectResult.area);
            object.put("auctionData", oneObjectResult.auctionData);
            object.put("closingApplicationsDate", oneObjectResult.closingApplicationsDate);
            object.put("guaranteeAmount", oneObjectResult.guaranteeAmount);
            object.put("lossFreeRental", oneObjectResult.lossFreeRental);
            object.put("monthlyProfit", oneObjectResult.monthlyProfit);
            object.put("monthlyCost", oneObjectResult.monthlyCost);
            object.put("monthlyRental", oneObjectResult.monthlyRental);
            object.put("rentalPeriod", oneObjectResult.rentalPeriod);
            object.put("monthlyHeating", oneObjectResult.monthlyHeating);
            object.put("housingOfficeMaintenance", oneObjectResult.housingOfficeMaintenance);
            object.put("yearProfit", oneObjectResult.yearProfit);
            object.put("yearRental", oneObjectResult.yearRental);
            object.put("yearInsurance", oneObjectResult.yearInsurance);
            object.put("priorRepair", oneObjectResult.priorRepair);

            allObjects.add(object);
        }

        return allObjects;
    }


    private Map<String, String> getReportSettings() {
        Map<String, String> settings = new HashMap<>();

        settings.put("minArea", Integer.toString(appConfig.minArea()));
        settings.put("maxArea", Integer.toString(appConfig.maxArea()));
        settings.put("minRentalPeriod", Integer.toString(appConfig.minRentalPeriod()));

        settings.put("propertyType", appConfig.propertyType());
        settings.put("contractType", appConfig.contractType());
        settings.put("country", appConfig.country());
        settings.put("propertyLocation", appConfig.propertyLocation());
        settings.put("sortFieldName", appConfig.sortFieldName());

        settings.put("averageRental", Integer.toString(appConfig.averageRental()));
        settings.put("profitMonths", Integer.toString(appConfig.profitMonths()));
        settings.put("priorRepair", Integer.toString(appConfig.priorRepair()));
        settings.put("contractRegistration", Integer.toString(appConfig.contractRegistration()));
        settings.put("runningCost", Integer.toString(appConfig.runningCost()));

        settings.put("insurance1metre", Integer.toString(appConfig.insurance1metre()));

        settings.put("monthlyHeating", Integer.toString(appConfig.monthlyHeating()));
        settings.put("housingOfficeMaintenance", Integer.toString(appConfig.housingOfficeMaintenance()));
        settings.put("accountingService", Integer.toString(appConfig.accountingService()));
        settings.put("requiredProfitMargin", Integer.toString(appConfig.requiredProfitMargin()));
        log.info("Tolerable/required profit margin: {}", appConfig.requiredProfitMargin());

        return settings;
    }

    /**
     * Подготовить заголовки столбцов
     * @return Коллекция заголовков для HTML таблицы
     */
    private List<String> getTableTitles() {
        List<String> titles = new ArrayList<>();

        titles.add("N");
        titles.add("Номер извещения");
        titles.add("Коэфф. доходности");
        titles.add("Адрес");
        titles.add("Площадь, кв.м");
        titles.add("Дата торгов");
        titles.add("Дата окончания подачи заявок");
        titles.add("Сумма залога");
        titles.add("Безубыточная сдача, руб/кв.м. в мес.");

        titles.add("Доход в месяц, руб.");
        titles.add("Расходы в месяц, руб.");
        titles.add("Выплата ренты в месяц, руб.");
        titles.add("Срок аренды");
        titles.add("Стоимость отопления в месяц, руб.");
        titles.add("Обслуживание ЖЭКом в месяц, руб.");

        titles.add("Доход в год, рублей&nbsp;");    // Костыльнул :-)
        titles.add("Выплата ренты в год, руб.");
        titles.add("Страховка за год, руб.");
        titles.add("Предварительный ремонт, руб.");

        return titles;
    }
}
