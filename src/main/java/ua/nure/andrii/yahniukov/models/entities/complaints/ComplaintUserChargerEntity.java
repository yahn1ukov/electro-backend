package ua.nure.andrii.yahniukov.models.entities.complaints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaints_user_charger")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ComplaintUserChargerEntity extends BaseComplaintEntity {
    @ManyToOne
    @JoinColumn(name = "charger_id", referencedColumnName = "id")
    private ChargerEntity charger;
}
