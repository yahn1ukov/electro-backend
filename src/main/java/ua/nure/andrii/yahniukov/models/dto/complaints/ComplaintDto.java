package ua.nure.andrii.yahniukov.models.dto.complaints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ComplaintDto {
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

    public static ComplaintDto fromComplaint(ComplaintUserChargerEntity complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFName() + " " + complaint.getUser().getLName())
                .company(complaint.getCharger().getOwner().getCompany())
                .name(complaint.getCharger().getName())
                .country(complaint.getCharger().getCountry())
                .city(complaint.getCharger().getCity())
                .street(complaint.getCharger().getStreet())
                .zipCode(complaint.getCharger().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }

    public static ComplaintDto fromComplaint(ComplaintUserStationEntity complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFName() + " " + complaint.getUser().getLName())
                .company(complaint.getStation().getOwner().getCompany())
                .country(complaint.getStation().getCountry())
                .city(complaint.getStation().getCity())
                .street(complaint.getStation().getStreet())
                .zipCode(complaint.getStation().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}
