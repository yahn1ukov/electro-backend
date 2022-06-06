package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.CascadeType.DELETE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = "stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Builder.Default
    @OneToMany(mappedBy = "station")
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "station")
    @Cascade({DELETE, SAVE_UPDATE})
    private List<CarNameEntity> carNames = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "station")
    @Cascade({DELETE, SAVE_UPDATE})
    private List<CarModelEntity> carModels = new ArrayList<>();

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

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = null;

    @Builder.Default
    @Column(name = "web_site")
    private String webSite = null;

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}