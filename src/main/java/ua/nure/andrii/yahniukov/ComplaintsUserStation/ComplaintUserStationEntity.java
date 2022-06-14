package ua.nure.andrii.yahniukov.ComplaintsUserStation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.Station.StationEntity;
import ua.nure.andrii.yahniukov.User.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "complaints_user_station")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintUserStationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    private StationEntity station;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}
