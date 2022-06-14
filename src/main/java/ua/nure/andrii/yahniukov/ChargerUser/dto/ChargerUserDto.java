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
public class ChargerUserDto {
    private Long id;
    private String email;
    private String company;
    private UserRole role;
    private Boolean isBlock;
    private Boolean isVerification;
    private String phoneNumber;
    private String webSite;
    private Date createdAt;

    public static ChargerUserDto fromPartner(ChargerUserEntity chargerUser) {
        return ChargerUserDto.builder()
                .id(chargerUser.getId())
                .email(chargerUser.getEmail())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .isBlock(chargerUser.getIsNotBlock())
                .isVerification(chargerUser.getIsVerification())
                .phoneNumber(chargerUser.getPhoneNumber())
                .webSite(chargerUser.getWebSite())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }
}
