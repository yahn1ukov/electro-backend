package ua.nure.andrii.yahniukov.security.dto.registration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequestPartnerDto {
    private String company;
    private String email;
    private String password;
}
