package ua.nure.andrii.yahniukov.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;

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
    private Float priceOfPerHour;
    private String company;
    private String typeConnector;
    private String phoneNumber;
    private String webSite;
    private String timeFrom;
    private String timeTo;

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
                .priceOfPerHour(charger.getPriceOfPerHour())
                .company(charger.getOwner().getCompany())
                .typeConnector(charger.getTypeConnector())
                .phoneNumber(charger.getOwner().getPhoneNumber())
                .webSite(charger.getOwner().getWebSite())
                .timeFrom(charger.getTimeFrom())
                .timeTo(charger.getTimeTo())
                .build();
    }
}
