package ua.nure.andrii.yahniukov.security.dto.registration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequestUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
