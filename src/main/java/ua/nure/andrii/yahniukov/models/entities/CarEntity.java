package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "vin_code", updatable = false, unique = true)
    private String vinCode;

    @Builder.Default
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
