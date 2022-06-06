package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "station")
    private List<ComplaintUserStationEntity> stationComplaints;

    @OneToMany(mappedBy = "station", cascade = CascadeType.REMOVE)
    private List<CarNameEntity> carNames;

    @OneToMany(mappedBy = "station", cascade = CascadeType.REMOVE)
    private List<CarModelEntity> carModels;

    @Column(name = "all_place")
    private Integer allPlace;

    @Column(name = "free_place")
    private Integer freePlace;

    @Column(name = "middle_price_for_per_hour")
    private Float middlePriceForPerHour;

    @Column(name = "time_from")
    private String timeFrom;

    @Column(name = "time_to")
    private String timeTo;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}