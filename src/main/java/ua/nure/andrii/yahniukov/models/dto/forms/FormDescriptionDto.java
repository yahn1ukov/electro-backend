package ua.nure.andrii.yahniukov.models.dto.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDescriptionDto {
    private String description;
}
