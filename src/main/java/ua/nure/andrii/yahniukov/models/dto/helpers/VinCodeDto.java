package ua.nure.andrii.yahniukov.models.dto.helpers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VinCodeDto {
    private String vinCode;
}
