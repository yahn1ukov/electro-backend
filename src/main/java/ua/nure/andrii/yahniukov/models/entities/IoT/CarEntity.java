package ua.nure.andrii.yahniukov.models.entities.IoT;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "vin_code", updatable = false, unique = true)
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

    @Column(name = "type_connector")
    private String typeConnector;

    @Column(name = "percentage_of_charge")
    private Double percentageOfCharge;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date updatedAt = new Date();
}
