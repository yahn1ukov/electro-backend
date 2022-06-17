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
public class StationUserNoVerificationDto {
    private Long id;
    private String company;
    private String email;
    private UserRole role;
    private Date createdAt;

    public static boolean noVerification(StationUserEntity stationUser) {
        return !stationUser.getIsVerification();
    }

    public static StationUserNoVerificationDto fromNoVerification(StationUserEntity stationUser) {
        return StationUserNoVerificationDto.builder()
                .id(stationUser.getId())
                .company(stationUser.getCompany())
                .email(stationUser.getEmail())
                .role(stationUser.getRole())
                .createdAt(stationUser.getCreatedAt())
                .build();
    }
}
