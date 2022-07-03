package ua.nure.andrii.yahniukov.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.station.StationEntity;
import ua.nure.andrii.yahniukov.user.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "complaints_users_stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintUserStationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}
