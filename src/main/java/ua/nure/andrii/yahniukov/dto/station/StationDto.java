package ua.nure.andrii.yahniukov.dto.station;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.station.StationEntity;

import java.util.Date;

@Data
@Builder
public class StationDto {
    private Long id;
    private String name;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private Double latitude;
    private Double longitude;
    private String carName;
    private String carModel;
    private Integer allPlace;
    private Integer freePlace;
    private String company;
    private Float middlePriceForPerHour;
    private String timeFrom;
    private String timeTo;
    private Date createAt;

    public static StationDto fromStation(StationEntity station) {
        return StationDto.builder()
                .id(station.getId())
                .name(station.getName())
                .country(station.getCountry())
                .city(station.getCity())
                .street(station.getStreet())
                .zipCode(station.getZipCode())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .carName(station.getCarName())
                .carModel(station.getCarModel())
                .allPlace(station.getAllPlace())
                .freePlace(station.getFreePlace())
                .company(station.getOwner().getCompany())
                .middlePriceForPerHour(station.getMiddlePriceForPerHour())
                .timeFrom(station.getTimeFrom())
                .timeTo(station.getTimeTo())
                .createAt(station.getCreatedAt())
                .build();
    }
}