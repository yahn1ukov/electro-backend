package ua.nure.andrii.yahniukov.StationUser.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.StationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class StationUserDto {
    private Long id;
    private String email;
    private String company;
    private UserRole role;
    private Boolean isBlock;
    private Boolean isVerification;
    private String phoneNumber;
    private String webSite;
    private Date createdAt;

    public static StationUserDto fromPartner(StationUserEntity stationUser) {
        return StationUserDto.builder()
                .id(stationUser.getId())
                .email(stationUser.getEmail())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .isBlock(stationUser.getIsNotBlock())
                .isVerification(stationUser.getIsVerification())
                .phoneNumber(stationUser.getPhoneNumber())
                .webSite(stationUser.getWebSite())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
