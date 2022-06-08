package ua.nure.andrii.yahniukov.models.dto.helpers;

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
public class PartnerVerificationDto {
    private Long id;
    private String company;
    private UserRole role;
    private Boolean isBlock;
    private Date createdAt;

    public static boolean verification(ChargerUserEntity chargerUser) {
        return chargerUser.getIsVerification();
    }

    public static boolean verification(StationUserEntity stationUser) {
        return stationUser.getIsVerification();
    }

    public static PartnerVerificationDto fromVerification(ChargerUserEntity chargerUser) {
        return PartnerVerificationDto.builder()
                .id(chargerUser.getId())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .isBlock(chargerUser.getIsBlock())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }

    public static PartnerVerificationDto fromVerification(StationUserEntity stationUser) {
        return PartnerVerificationDto.builder()
                .id(stationUser.getId())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .isBlock(stationUser.getIsBlock())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
