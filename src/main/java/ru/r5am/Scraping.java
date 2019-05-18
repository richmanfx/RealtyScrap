package ru.r5am;

import ru.r5am.pageobjects.RentalPage;

class Scraping {

    static void scrap() {

        setSearchFilters();     // Выставить фильтры поиска

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

//        // Указать вид договора
//        rentalPage.setContractType(settings);
//
//        // Указать страну
//        rentalPage.setCountry();
//
//        // Указать местоположение имущества
//        rentalPage.setPropertyLocation(settings);
//
//        // Указать диапазон площади объекта
//        rentalPage.setObjectAreaRange(settings);
//
//        // Указать минимальный срок аренды
//        rentalPage.setRentalPeriod(settings);
    }
}
