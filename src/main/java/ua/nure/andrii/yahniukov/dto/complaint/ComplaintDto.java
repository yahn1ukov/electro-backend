package ua.nure.andrii.yahniukov.dto.complaint;

import lombok.Builder;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserStationEntity;

import java.util.Date;

@Builder
public class ComplaintDto {
    private Long id;
    private String fullName;
    private String company;
    private String code;
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

    public static ComplaintDto fromComplaint(ComplaintUserStationEntity complaint) {
        return ComplaintDto.builder()
                .id(complaint.getId())
                .fullName(complaint.getUser().getFirstName() + " " + complaint.getUser().getLastName())
                .company(complaint.getStation().getOwner().getCompany())
                .name(complaint.getStation().getName())
                .country(complaint.getStation().getCountry())
                .city(complaint.getStation().getCity())
                .street(complaint.getStation().getStreet())
                .zipCode(complaint.getStation().getZipCode())
                .description(complaint.getDescription())
                .createdAt(complaint.getCreatedAt())
                .build();
    }
}