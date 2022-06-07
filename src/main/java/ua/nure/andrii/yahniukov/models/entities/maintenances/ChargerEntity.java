package ua.nure.andrii.yahniukov.models.entities.maintenances;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chargers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerEntity extends BaseMaintenanceEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private ChargerUserEntity owner;


    @Column(name = "name", unique = true)
    private String name;

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

    @Column(name = "type_connector")
    private String typeConnector;

    @Builder.Default
    @OneToMany(mappedBy = "charger")
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();
}