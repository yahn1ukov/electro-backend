package ua.nure.andrii.yahniukov.ComplaintsUserCharger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.ComplaintUserChargerEntity;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ComplaintUserChargerDto {
    private Long id;
    private String fullName;
    private String company;
    private String code;
    private String country;
    private String city;
    private String street;
    private Integer zipCode;
    private String description;
    private Date createdAt;

    public static ComplaintUserChargerDto fromComplaint(ComplaintUserChargerEntity complaint) {
        return ComplaintUserChargerDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFirstName() + " " + complaint.getUser().getLastName())
                .company(complaint.getCharger().getOwner().getCompany())
                .code(complaint.getCharger().getCode())
                .country(complaint.getCharger().getCountry())
                .city(complaint.getCharger().getCity())
                .street(complaint.getCharger().getStreet())
                .zipCode(complaint.getCharger().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}
