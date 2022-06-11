package ua.nure.andrii.yahniukov.models.dto.maintenances;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StationDto extends BaseMaintenancesDto {
    private Long id;
    private String company;
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
    private Float middlePriceForPerHour;
    private String timeFrom;
    private String timeTo;
    private String phoneNumber;
    private String webSite;

    public static boolean isRadius(StationEntity station, Long carLatitude, Long carLongitude) {
        int radius = calculateDistanceInKilometer(carLatitude, carLongitude, station.getLatitude(), station.getLongitude());
        return radius <= 10;
    }

    public static boolean isFreePlace(StationEntity station) {
        return station.getFreePlace() < station.getAllPlace();
    }

    public static boolean isModel(StationEntity station, String carModel) {
        return station.getCarModel().equals(carModel);
    }

    public static boolean isName(StationEntity station, String carName) {
        return station.getCarName().equals(carName);
    }

    public static StationDto fromStation(StationEntity station) {
        return StationDto.builder()
                .id(station.getId())
                .company(station.getOwner().getCompany())
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
                .middlePriceForPerHour(station.getMiddlePriceForPerHour())
                .timeFrom(station.getTimeFrom())
                .timeTo(station.getTimeTo())
                .phoneNumber(station.getOwner().getPhoneNumber())
                .webSite(station.getOwner().getWebSite())
                .build();
    }
}
