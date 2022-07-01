package ua.nure.andrii.yahniukov.dto.stationUser;

import lombok.Builder;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;

import java.util.Date;

@Builder
public class StationUserDto {
    private Long id;
    private String email;
    private String company;
    private UserRole role;
    private Boolean isNotBlock;
    private Date createdAt;

    public static boolean isVerification(StationUserEntity stationUser) {
        return stationUser.getIsVerification();
    }

    public static boolean isNotVerification(StationUserEntity stationUser) {
        return !stationUser.getIsVerification();
    }

    public static StationUserDto fromStationUser(StationUserEntity stationUser) {
        return StationUserDto.builder()
                .id(stationUser.getId())
                .email(stationUser.getEmail())
                .company(stationUser.getCompany())
                .role(stationUser.getRole())
                .isNotBlock(stationUser.getIsNotBlock())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
