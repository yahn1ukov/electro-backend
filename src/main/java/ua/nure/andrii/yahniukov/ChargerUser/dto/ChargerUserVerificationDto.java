package ua.nure.andrii.yahniukov.ChargerUser.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ChargerUserVerificationDto {
    private Long id;
    private String company;
    private String email;
    private UserRole role;
    private Boolean isNotBlock;
    private Date createdAt;

    public static boolean verification(ChargerUserEntity chargerUser) {
        return chargerUser.getIsVerification();
    }

    public static ChargerUserVerificationDto fromVerification(ChargerUserEntity chargerUser) {
        return ChargerUserVerificationDto.builder()
                .id(chargerUser.getId())
                .company(chargerUser.getCompany())
                .email(chargerUser.getEmail())
                .role(chargerUser.getRole())
                .isNotBlock(chargerUser.getIsNotBlock())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }
}
