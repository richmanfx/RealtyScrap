package ru.r5am;


import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static com.codeborne.selenide.Selenide.open;

import ru.r5am.pageobjects.RentalPage;
import ru.r5am.pageobjects.RealtyObjectPage;
import ru.r5am.pageobjects.SearchResultPage;


class Scraping {

    private static final Logger log = LogManager.getRootLogger();
    private static ArrayList<ObjectInfo> allObjectsInfo = new ArrayList<>();

    static ArrayList<ObjectInfo> scrap() {

        setSearchFilters();     // Выставить фильтры поиска
        search();        // Искать

        // Определить количество найденных объектов
        int quantity = getObjectsQuantity();
        log.info("Количество найденных объектов: {}", quantity);

        // Собрать информацию по объектам на всех страницах
        getAllObjectsInfo();

        // Собрать дополнительную информацию по каждому объекту
        getAllObjectAdditionalInfo();

        log.info("Всего обработано объектов: {}", allObjectsInfo.size());

        return allObjectsInfo;
    }

    /**
     * Собрать дополнительную информацию по каждому объекту
     */
    private static void getAllObjectAdditionalInfo() {

        RealtyObjectPage realtyObjectPage = new RealtyObjectPage();

        // TODO: Такая идея: Класс результатов большой наследовать от инфо маленького с переносом части данных!

        for(int index=0; index < allObjectsInfo.size(); index++) {

            log.info(String.format(
                    "Всего объектов: '%d', обрабатывается объект: '%d'", allObjectsInfo.size(), index + 1));

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

        // **********************************************************
        // Для отладки - скрапить только первую страницу закомментировать всё дальше
        // **********************************************************

//        // Есть ли следующая страница
//        boolean nextPageExist = searchResultPage.existNextPage();
//
//        if(nextPageExist) { // Условие выхода из рекурсии - нет следующей страницы
//            // Перейти на следующую страницу
//            searchResultPage.goToNextPage();
//
//            // Рекурсия
//            getAllObjectsInfo();
//        }

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
            info.monthlyRental = rents.get(index).replace(",", ".").replace(" ", "").replace("руб.", "");
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
        String propertyType = "Помещение";  // TODO: вынести в конфиг
        rentalPage.setAuctionType(propertyType);

        // Указать вид договора
        String contractType = "Договор аренды";
        rentalPage.setContractType(contractType);

        // Указать страну
        rentalPage.setCountry();

        // Указать местоположение имущества
        String PropertyLocation = "Москва (г)";   // TODO: вынести в конфиг
        rentalPage.setPropertyLocation(PropertyLocation);

        // Указать диапазон площади объекта
        int minArea = 10;          // TODO: вынести в конфиг
        int maxArea = 75;
        rentalPage.setObjectAreaRange(minArea, maxArea);

        // Указать минимальный срок аренды
        int minRentalPeriod = 5;        // TODO: вынести в конфиг
        rentalPage.setRentalPeriod(minRentalPeriod);
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
            log.info("Объекты не нашлись");
        }
    }
}
