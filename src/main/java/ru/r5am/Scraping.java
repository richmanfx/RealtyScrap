package ru.r5am;


import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ru.r5am.pageobjects.RentalPage;
import ru.r5am.pageobjects.SearchResultPage;


class Scraping {

    private static final Logger log = LogManager.getRootLogger();

    static void scrap() {

        setSearchFilters();     // Выставить фильтры поиска
        search();        // Искать

        // Определить количество найденных объектов
        int quantity = getObjectsQuantity();
        log.info("Количество найденных объектов: {}", quantity);

        // Собрать информацию по объектам на всех страницах
        getAllObjectsInfo();

    }

    /**
     * Собрать информацию по объектам на всех страницах
     */
    private static void getAllObjectsInfo() {

        // Собрать иформацию об объектах на текущей странице
        ArrayList<ObjectInfo> objectsInfo = getOnePageObjectsInfo();
        for(ObjectInfo object: objectsInfo)
        log.info(object.notificationNumber + " / " +
                 object.area + " / " +
                 object.monthlyRental + " / " +
                 object.rentalPeriod + " / " +
                 object.webLink
        );

        // Добавить к основной коллекци


        // Есть ли следующая страница


//        if len(nextPage) < 1 { // Условие выхода из рекурсии - нет следующей страницы
//            // Выходим
//            return
//        } else {
//            // Перейти на следующую страницу
//            pageobjects.GoToNextPage(webDriver)
//
//            // Рекурсия
//            ObjectInfoCollect(webDriver)
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
