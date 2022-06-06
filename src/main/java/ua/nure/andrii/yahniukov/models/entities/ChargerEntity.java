package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

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
    @Column(name = "is_charging")
    private Boolean isCharging = false;

    @Builder.Default
    @Column(name = "is_broken")
    private Boolean isBroken = false;

    @Column(name = "is_fast")
    private Boolean isFast;

    @Column(name = "is_pay")
    private Boolean isPay;

    @Column(name = "price_of_per_hour")
    private Float priceOfPerHour;

    @Column(name = "company")
    private String company;

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = null;

    @Builder.Default
    @Column(name = "web_site")
    private String webSite = null;

    @Column(name = "time_from")
    private String timeFrom;

    @Column(name = "time_to")
    private String timeTo;

    @Column(name = "type_connector")
    private String typeConnector;

    @Builder.Default
    @OneToMany(mappedBy = "charger")
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}