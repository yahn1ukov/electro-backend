package ua.nure.andrii.yahniukov.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ua.nure.andrii.yahniukov.enums.UserRole;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormRoleDto {
    private UserRole role;
}
