package ua.nure.andrii.yahniukov.iot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.user.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "vin_code", unique = true)
    private String vinCode;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @Builder.Default
    private UserEntity owner = null;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "type_connector")
    private String typeConnector;

    @Column(name = "percentage_of_charge")
    private Double percentageOfCharge;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}