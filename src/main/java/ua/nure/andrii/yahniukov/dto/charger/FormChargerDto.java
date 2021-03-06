package ua.nure.andrii.yahniukov.dto.charger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormChargerDto {
    private String code;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private Double latitude;
    private Double longitude;
    private Boolean isFast;
    private Boolean isPay;
    private Float priceOfPerHour;
    private String typeConnector;
    private String timeFrom;
    private String timeTo;
}