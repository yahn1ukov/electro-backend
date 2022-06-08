package ua.nure.andrii.yahniukov.models.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PartnerDto {
    private Long id;
    private String email;
    private String company;
    private UserRole role;
    private Boolean isBlock;
    private Boolean isVerification;
    private String phoneNumber;
    private String webSite;
    private Date createdAt;

    public static PartnerDto fromPartner(ChargerUserEntity chargerUser) {
        return PartnerDto.builder()
                .id(chargerUser.getId())
                .email(chargerUser.getEmail())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .isBlock(chargerUser.getIsBlock())
                .isVerification(chargerUser.getIsVerification())
                .phoneNumber(chargerUser.getPhoneNumber())
                .webSite(chargerUser.getWebSite())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }

    public static PartnerDto fromPartner(StationUserEntity stationUser) {
        return PartnerDto.builder()
                .id(stationUser.getId())
                .email(stationUser.getEmail())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .isBlock(stationUser.getIsBlock())
                .isVerification(stationUser.getIsVerification())
                .phoneNumber(stationUser.getPhoneNumber())
                .webSite(stationUser.getWebSite())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}