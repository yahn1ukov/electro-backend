package ua.nure.andrii.yahniukov.models.entities.complaints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaints_user_station")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintUserStationEntity extends BaseComplaintEntity {
    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private StationEntity station;
}
