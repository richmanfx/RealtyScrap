package ru.r5am;


import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.open;

import ru.r5am.pageobjects.RentalPage;
import ru.r5am.pageobjects.RealtyObjectPage;
import ru.r5am.pageobjects.SearchResultPage;


class Scraping {

    Scraping() {
    }

    private static final Logger log = LogManager.getRootLogger();
    private static ArrayList<ObjectInfo> allObjectsInfo = new ArrayList<>();
    private static AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    static ArrayList<ObjectInfo> scrap() {

        setSearchFilters();     // Выставить фильтры поиска
        search();           // Искать

        // Определить количество найденных объектов
        int quantity = getObjectsQuantity();
        log.info("Number of objects found: {}", quantity);

        // Собрать информацию по объектам на всех страницах
        getAllObjectsInfo();

        // Собрать дополнительную информацию по каждому объекту
        getAllObjectAdditionalInfo();

        log.info("Total processed objects: {}", allObjectsInfo.size());

        return allObjectsInfo;
    }

    /**
     * Собрать дополнительную информацию по каждому объекту
     */
    private static void getAllObjectAdditionalInfo() {

        RealtyObjectPage realtyObjectPage = new RealtyObjectPage();

        for(int index=0; index < allObjectsInfo.size(); index++) {

            log.info("Total objects: '{}', in processing: '{}'", allObjectsInfo.size(), index + 1);

            // Перейти на страницу объекта недвижимости
            open(allObjectsInfo.get(index).webLink);

            // Сумма залога
            allObjectsInfo.get(index).guaranteeAmount = realtyObjectPage.getDeposit();

            // Адрес
            allObjectsInfo.get(index).address = realtyObjectPage.getAddress();

            // На закладку "Общие"
            realtyObjectPage.toGeneralTab();

            // Дата торгов
            allObjectsInfo.get(index).auctionData = realtyObjectPage.getAuctionData();

            // Дата окончания подачи заявок
            allObjectsInfo.get(index).closingApplicationsDate = realtyObjectPage.getClosingApplicationsDate();
        }
    }

    /**
     * Собрать информацию по объектам на всех страницах
     */
    private static void getAllObjectsInfo() {

        SearchResultPage searchResultPage = new SearchResultPage();

        // Собрать иформацию об объектах на текущей странице
        ArrayList<ObjectInfo> objectsInfo = getOnePageObjectsInfo();

        // Добавить к основной коллекци
        allObjectsInfo.addAll(objectsInfo);

        // *******************************************************************************
        // Для отладки: скрапить только первую страницу - закомментировать дальше
        // *******************************************************************************

        // Есть ли следующая страница
        boolean nextPageExist = searchResultPage.existNextPage();

        if(nextPageExist) { // Условие выхода из рекурсии - нет следующей страницы
            // Перейти на следующую страницу
            searchResultPage.goToNextPage();

            // Рекурсия
            getAllObjectsInfo();
        }

        // *******************************************************************************
        // Для отладки: скрапить только первую страницу - закомментировать до этого места
        // *******************************************************************************

    }

    /**
     * Собрать иформацию об объектах на текущей странице
     * @return Коллекция с информацией
     */
    private static ArrayList<ObjectInfo> getOnePageObjectsInfo() {

        SearchResultPage searchResultPage = new SearchResultPage();
        ArrayList<ObjectInfo> objectInfos = new ArrayList<>();

        // Количество объектов только на данной странице
        int objectsQuantity = searchResultPage.getLotsOnPage();

        // Извещения
        List<String> notices = searchResultPage.getNoticeNumbers();

        // Площади объектов
        List<String> areas = searchResultPage.getAreas();

        // Стоимость аренды в месяц
        List<String> rents = searchResultPage.getRents();

        // Срок аренды
        List<String> rentalPeriods = searchResultPage.getRentalPeriods();

        // Ссылка для просмотра
        List<String> links = searchResultPage.getLinks();

        for( int index = 0; index < objectsQuantity; index++) {
            ObjectInfo info = new ObjectInfo();
            info.notificationNumber = notices.get(index);
            info.area = areas.get(index).replace(" м²", "");
            info.monthlyRental = Integer.toString(
                    Math.round(
                            Float.parseFloat(rents.get(index).replace(",", ".").replace(" ", "").replace("руб.", ""))
                    )
            );
            info.rentalPeriod = rentalPeriods.get(index);
            info.webLink = links.get(index);

            objectInfos.add(info);
        }

        return objectInfos;
    }

    /**
     * Определить количество найденных объектов
     * @return Количество объектов
     */
    private static int getObjectsQuantity() {

        return new SearchResultPage().getLotsQuantity();

    }

    /**
     * Выставить фильтры поиска
     */
    private static void setSearchFilters() {

        RentalPage rentalPage = new RentalPage();

        // Войти в расширенный поиск
        rentalPage.comeInExtSearch();

        // Выбрать тип торгов
        rentalPage.setTradesType();

        // Указать тип имущества
        rentalPage.setAuctionType(appConfig.propertyType());

        // Указать вид договора
        rentalPage.setContractType(appConfig.contractType());

        // Указать страну
        rentalPage.setCountry(appConfig.country());

        // Указать местоположение имущества
        rentalPage.setPropertyLocation(appConfig.propertyLocation());

        // Указать диапазон площади объекта
        rentalPage.setObjectAreaRange(appConfig.minArea(), appConfig.maxArea());

        // Указать минимальный срок аренды
        rentalPage.setRentalPeriod(appConfig.minRentalPeriod());
    }

    /**
     * Выполнить поиск объектов
     */
    private static void search() {

        RentalPage rentalPage = new RentalPage();
        SearchResultPage searchResultPage = new SearchResultPage();

        // Начать поиск
        rentalPage.searchButtonClick();

        // Дождаться отображения объектов
        boolean show = searchResultPage.isObjectsShow();
        if(!show) {
            log.info("Objects not found");
        }
    }
}
