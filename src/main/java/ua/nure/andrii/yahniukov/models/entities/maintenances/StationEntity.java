package ua.nure.andrii.yahniukov.models.entities.maintenances;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StationEntity extends BaseMaintenanceEntity {
    @Builder.Default
    @OneToMany(mappedBy = "station")
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

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
}