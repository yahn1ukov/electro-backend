package ua.nure.andrii.yahniukov.models.dto.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormCarDto {
    private String name;
    private String model;
    private String vinCode;
    private Integer mileage;
    private String typeConnector;
    private Double percentageOfCharge;
}
