package ua.nure.andrii.yahniukov.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserDto {
    private String email;
    private String fName;
    private String lName;
    private String password;
}
