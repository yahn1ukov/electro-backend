package ua.nure.andrii.yahniukov.security.dto.login;

import lombok.Builder;
import ua.nure.andrii.yahniukov.enums.UserRole;

@Builder
public class LoginResponseDto {
    private Long id;
    private String token;
    private UserRole role;
}
