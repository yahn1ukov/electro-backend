package ua.nure.andrii.yahniukov.dto.iot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddCarDto {
    private String name;
    private String model;
    private String vinCode;
    private Integer mileage;
    private String typeConnector;
    private Double percentageOfCharge;
}