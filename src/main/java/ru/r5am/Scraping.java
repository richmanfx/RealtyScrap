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

//        // Указать минимальный срок аренды
//        rentalPage.setRentalPeriod(settings);
    }
}
