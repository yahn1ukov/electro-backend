package ua.nure.andrii.yahniukov.dto.charger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.charger.ChargerEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ChargerDto {
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    private Long id;
    private String code;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private Double latitude;
    private Double longitude;
    private Boolean isFast;
    private Boolean isPay;
    private String company;
    private String phoneNumber;
    private String webSite;
    private Float priceOfPerHour;
    private String typeConnector;
    private String timeFrom;
    private String timeTo;
    private Date createAt;

    public static int calculateDistanceInKilometer(double carLat, double carLng,
                                                   double stationLat, double stationLng) {

        double latDistance = Math.toRadians(carLat - stationLat);
        double lngDistance = Math.toRadians(carLng - stationLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(carLat)) * Math.cos(Math.toRadians(stationLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    public static boolean isRadius(ChargerEntity charger, Long carLatitude, Long carLongitude, Integer radius) {
        int calcRadius = calculateDistanceInKilometer(carLatitude, carLongitude, charger.getLatitude(), charger.getLongitude());
        return calcRadius <= radius;
    }

    public static boolean isNoCharging(ChargerEntity charger) {
        return !charger.getIsCharging();
    }

    public static boolean isNoBroken(ChargerEntity charger) {
        return !charger.getIsBroken();
    }

    public static boolean isFast(ChargerEntity charger, Long percentOfBattery) {
        return (percentOfBattery < 20) == charger.getIsFast();
    }

    public static boolean isTypeConnector(ChargerEntity charger, String typeConnector) {
        return charger.getTypeConnector().equals(typeConnector);
    }

    public static ChargerDto fromCharger(ChargerEntity charger) {
        return ChargerDto.builder()
                .id(charger.getId())
                .code(charger.getCode())
                .country(charger.getCountry())
                .city(charger.getCity())
                .street(charger.getStreet())
                .zipCode(charger.getZipCode())
                .latitude(charger.getLatitude())
                .longitude(charger.getLongitude())
                .isFast(charger.getIsFast())
                .isPay(charger.getIsPay())
                .company(charger.getOwner().getCompany())
                .phoneNumber(charger.getOwner().getPhoneNumber())
                .webSite(charger.getOwner().getWebSite())
                .priceOfPerHour(charger.getPriceOfPerHour())
                .typeConnector(charger.getTypeConnector())
                .timeFrom(charger.getTimeFrom())
                .timeTo(charger.getTimeTo())
                .createAt(charger.getCreatedAt())
                .build();
    }
}
