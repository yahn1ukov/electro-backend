package ua.nure.andrii.yahniukov.dto.charger;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.charger.ChargerEntity;

import java.util.Date;

@Data
@Builder
public class ChargerDto {
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
    private Float priceOfPerHour;
    private String typeConnector;
    private String timeFrom;
    private String timeTo;
    private Date createAt;

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
                .priceOfPerHour(charger.getPriceOfPerHour())
                .typeConnector(charger.getTypeConnector())
                .timeFrom(charger.getTimeFrom())
                .timeTo(charger.getTimeTo())
                .createAt(charger.getCreatedAt())
                .build();
    }
}