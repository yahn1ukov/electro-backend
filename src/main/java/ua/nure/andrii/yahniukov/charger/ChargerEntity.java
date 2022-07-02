package ua.nure.andrii.yahniukov.charger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserChargerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "chargers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerEntity {
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

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private ChargerUserEntity owner;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "is_charging")
    @Builder.Default
    private Boolean isCharging = false;

    @Column(name = "is_broken")
    @Builder.Default
    private Boolean isBroken = false;

    @Column(name = "is_fast")
    private Boolean isFast;

    @Column(name = "is_pay")
    private Boolean isPay;

    @Column(name = "price_of_per_hour")
    private Float priceOfPerHour;

    @Column(name = "type_connector")
    private String typeConnector;

    @OneToMany(mappedBy = "charger")
    @Builder.Default
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();

    @Column(name = "time_from")
    private String timeFrom;

    @Column(name = "time_to")
    private String timeTo;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}