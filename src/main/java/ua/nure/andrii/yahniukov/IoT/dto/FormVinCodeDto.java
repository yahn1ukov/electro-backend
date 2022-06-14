package ua.nure.andrii.yahniukov.IoT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormVinCodeDto {
    private String vinCode;
}
