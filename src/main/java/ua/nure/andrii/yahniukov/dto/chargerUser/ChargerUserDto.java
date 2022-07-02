package ua.nure.andrii.yahniukov.dto.chargerUser;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import java.util.Date;

@Data
@Builder
public class ChargerUserDto {
    private Long id;
    private String email;
    private String company;
    private UserRole role;
    private Boolean isNotBlock;
    private Date createdAt;

    public static boolean isVerification(ChargerUserEntity chargerUser) {
        return chargerUser.getIsVerification();
    }

    public static boolean isNotVerification(ChargerUserEntity chargerUser) {
        return !chargerUser.getIsVerification();
    }

    public static ChargerUserDto fromChargerUser(ChargerUserEntity chargerUser) {
        return ChargerUserDto.builder()
                .id(chargerUser.getId())
                .email(chargerUser.getEmail())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .isNotBlock(chargerUser.getIsNotBlock())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }
}
