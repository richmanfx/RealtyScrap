package ru.r5am;


import lombok.Getter;
import lombok.Setter;


class ObjectInfo {

    @Getter @Setter String  notificationNumber;         // Номер извещения
    @Getter @Setter String  address;                    // Адрес
    @Getter @Setter String  area;                       // Площадь, кв.м
    @Getter @Setter String  monthlyRental;              // Стоимость аренды в месяц, рублей
    @Getter @Setter String  rentalPeriod;               // Срок аренды, месяцев
    @Getter @Setter String  webLink;                    // Ссылка для просмотра
    @Getter @Setter String  auctionData;                // Дата проведения аукциона
    @Getter @Setter String  closingApplicationsDate;    // Дата окончания подачи заявок
    @Getter @Setter String  guaranteeAmount;            // Информация про залог

}
