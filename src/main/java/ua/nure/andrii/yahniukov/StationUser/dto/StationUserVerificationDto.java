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
public class StationUserVerificationDto {
    private Long id;
    private String company;
    private UserRole role;
    private Boolean isBlock;
    private Date createdAt;

    public static boolean verification(StationUserEntity stationUser) {
        return stationUser.getIsVerification();
    }

    public static StationUserVerificationDto fromVerification(StationUserEntity stationUser) {
        return StationUserVerificationDto.builder()
                .id(stationUser.getId())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .isBlock(stationUser.getIsNotBlock())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
