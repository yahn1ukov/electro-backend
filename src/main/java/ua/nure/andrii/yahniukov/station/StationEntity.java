package ua.nure.andrii.yahniukov.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Builder.Default
    @OneToMany(mappedBy = "station")
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "carName")
    private String carName;

    @Column(name = "carModel")
    private String carModel;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private StationUserEntity owner;

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

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}