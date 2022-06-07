package ua.nure.andrii.yahniukov.security.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterPartnerDto {
    private String company;
    private String email;
    private String password;
}
