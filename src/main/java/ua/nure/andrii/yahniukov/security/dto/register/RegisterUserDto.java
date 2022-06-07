package ua.nure.andrii.yahniukov.security.dto.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ua.nure.andrii.yahniukov.enums.UserRole;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserDto {
    private String email;
    private String fName;
    private String lName;
    private String password;
    private UserRole role;
    private Boolean isVerification;
}
