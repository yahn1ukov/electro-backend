package ua.nure.andrii.yahniukov.security.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
