package ua.nure.andrii.yahniukov.security.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    private String email;
    private String password;
}
