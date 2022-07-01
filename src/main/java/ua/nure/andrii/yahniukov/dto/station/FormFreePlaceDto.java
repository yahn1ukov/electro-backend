package ua.nure.andrii.yahniukov.dto.station;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormFreePlaceDto {
    private Integer freePlace;
}
