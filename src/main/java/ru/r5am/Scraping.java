package ru.r5am;


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
