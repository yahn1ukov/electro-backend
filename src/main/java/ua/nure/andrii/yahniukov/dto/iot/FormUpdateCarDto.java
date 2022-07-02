package ua.nure.andrii.yahniukov.dto.iot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormUpdateCarDto {
    private Double latitude;
    private Double longitude;
    private Double percentageOfCharge;
}