package ua.nure.andrii.yahniukov.models.dto;

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
public class NoVerificationPartnerDto {
    private Long id;
    private String company;
    private UserRole role;
    private Date createdAt;

    public static NoVerificationPartnerDto fromChargerUser(ChargerUserEntity chargerUser) {
        return NoVerificationPartnerDto.builder()
                .id(chargerUser.getId())
                .company(chargerUser.getCompany())
                .role(chargerUser.getRole())
                .createdAt(chargerUser.getCreatedAt())
                .build();
    }

    public static NoVerificationPartnerDto fromStationUser(StationUserEntity stationUser) {
        return NoVerificationPartnerDto.builder()
                .id(stationUser.getId())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}