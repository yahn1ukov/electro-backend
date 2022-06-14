package ua.nure.andrii.yahniukov.Station.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.Station.StationEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StationDto {
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
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

    public static boolean isRadius(StationEntity station, Long carLatitude, Long carLongitude, Integer radius) {
        int calcRadius = calculateDistanceInKilometer(carLatitude, carLongitude, station.getLatitude(), station.getLongitude());
        return calcRadius <= radius;
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
