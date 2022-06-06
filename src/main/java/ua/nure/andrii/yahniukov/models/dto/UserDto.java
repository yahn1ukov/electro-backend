package ua.nure.andrii.yahniukov.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String fName;
    private String lName;
    private UserRole role;
    private Boolean isBlock;
    private Boolean isVerification;
    private Date createdAt;

    public static UserDto fromUser(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .fName(user.getFName())
                .lName(user.getLName())
                .email(user.getEmail())
                .role(user.getRole())
                .isBlock(user.getIsBlock())
                .isVerification(user.getIsVerification())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
