package ua.nure.andrii.yahniukov.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ComplaintUserStationDto {
    private Long id;
    private String fullName;
    private String company;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private String description;
    private Date createdAt;

    public static ComplaintUserStationDto fromComplaintUserStation(ComplaintUserStationEntity complaint) {
        return ComplaintUserStationDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFName() + " " + complaint.getUser().getLName())
                .company(complaint.getStation().getCompany())
                .country(complaint.getStation().getCountry())
                .city(complaint.getStation().getCity())
                .street(complaint.getStation().getStreet())
                .zipCode(complaint.getStation().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}
