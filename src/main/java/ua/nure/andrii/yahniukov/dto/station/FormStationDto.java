package ua.nure.andrii.yahniukov.dto.station;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class FormStationDto {
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
    private String timeFrom;
    private String timeTo;
    private Float middlePriceForPerHour;
}

