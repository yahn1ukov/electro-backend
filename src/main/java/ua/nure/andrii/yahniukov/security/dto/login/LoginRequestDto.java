package ua.nure.andrii.yahniukov.security.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestDto {
    private String email;
    private String password;
}
