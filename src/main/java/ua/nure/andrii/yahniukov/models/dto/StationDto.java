package ua.nure.andrii.yahniukov.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.CarModelEntity;
import ua.nure.andrii.yahniukov.models.entities.CarNameEntity;
import ua.nure.andrii.yahniukov.models.entities.StationEntity;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StationDto {
    private Long id;
    private String company;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private Double latitude;
    private Double longitude;
    private List<CarNameEntity> carNames;
    private List<CarModelEntity> carModels;
    private Integer allPlace;
    private Integer freePlace;
    private Float middlePriceForPerHour;
    private String timeFrom;
    private String timeTo;
    private String phoneNumber;
    private String webSite;

    public static StationDto fromStation(StationEntity station) {
        return StationDto.builder()
                .id(station.getId())
                .company(station.getCompany())
                .country(station.getCountry())
                .city(station.getCity())
                .street(station.getStreet())
                .zipCode(station.getZipCode())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .carNames(station.getCarNames())
                .carModels(station.getCarModels())
                .allPlace(station.getAllPlace())
                .freePlace(station.getFreePlace())
                .middlePriceForPerHour(station.getMiddlePriceForPerHour())
                .timeFrom(station.getTimeFrom())
                .timeTo(station.getTimeTo())
                .phoneNumber(station.getPhoneNumber())
                .webSite(station.getWebSite())
                .build();
    }
}
