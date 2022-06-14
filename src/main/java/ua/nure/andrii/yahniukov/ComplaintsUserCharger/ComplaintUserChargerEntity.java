package ua.nure.andrii.yahniukov.ComplaintsUserCharger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.Charger.ChargerEntity;
import ua.nure.andrii.yahniukov.User.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "complaints_user_charger")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintUserChargerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "charger_id", referencedColumnName = "id")
    private ChargerEntity charger;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}
