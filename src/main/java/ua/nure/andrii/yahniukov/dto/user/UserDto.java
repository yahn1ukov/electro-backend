package ua.nure.andrii.yahniukov.dto.user;

import lombok.Builder;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.user.UserEntity;

import java.util.Date;

@Builder
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private Boolean isNotBlock;
    private Date createdAt;

    public static UserDto fromUser(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(String.format("%s %s", user.getFirstName(), user.getLastName()))
                .role(user.getRole())
                .isNotBlock(user.getIsNotBlock())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
