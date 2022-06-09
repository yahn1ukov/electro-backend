package ua.nure.andrii.yahniukov.models.dto.maintenances;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ChargerDto {
    private Long id;
    private String name;
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
        return typeConnector.equals(charger.getTypeConnector());
    }

    public static ChargerDto fromCharger(ChargerEntity charger) {
        return ChargerDto.builder()
                .id(charger.getId())
                .name(charger.getName())
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
