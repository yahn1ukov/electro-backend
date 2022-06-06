package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "carModels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private StationEntity station;
}
