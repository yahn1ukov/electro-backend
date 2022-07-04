package ua.nure.andrii.yahniukov.security.dto.login;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.enums.UserRole;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private UserRole role;
}
