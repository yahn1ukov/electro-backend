package ua.nure.andrii.yahniukov.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ComplaintUserChargerDto {
    private Long id;
    private String fullName;
    private String company;
    private String name;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private String description;
    private Date createdAt;

    public static ComplaintUserChargerDto fromComplaintUserCharger(ComplaintUserChargerEntity complaint) {
        return ComplaintUserChargerDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFName() + " " + complaint.getUser().getLName())
                .company(complaint.getCharger().getCompany())
                .name(complaint.getCharger().getName())
                .country(complaint.getCharger().getCountry())
                .city(complaint.getCharger().getCity())
                .street(complaint.getCharger().getStreet())
                .zipCode(complaint.getCharger().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}
