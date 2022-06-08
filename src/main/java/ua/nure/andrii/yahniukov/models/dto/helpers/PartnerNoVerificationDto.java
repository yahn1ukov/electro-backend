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
public class PartnerNoVerificationDto {
    private Long id;
    private String company;
    private UserRole role;
    private Date createdAt;

    public static boolean noVerification(ChargerUserEntity chargerUser) {
        return !chargerUser.getIsVerification();
    }

    public static boolean noVerification(StationUserEntity stationUser) {
        return !stationUser.getIsVerification();
    }

    public static PartnerNoVerificationDto fromNoVerification(ChargerUserEntity chargerUser) {
        return PartnerNoVerificationDto.builder()
                .id(chargerUser.getId())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }

    public static PartnerNoVerificationDto fromNoVerification(StationUserEntity stationUser) {
        return PartnerNoVerificationDto.builder()
                .id(stationUser.getId())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
