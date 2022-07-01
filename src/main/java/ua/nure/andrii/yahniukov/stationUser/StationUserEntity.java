package ua.nure.andrii.yahniukov.stationUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.station.StationEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "station_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationUserEntity {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    @Builder.Default
    List<StationEntity> stations = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "company", unique = true)
    private String company;
    @Column(name = "is_not_block")
    @Builder.Default
    private Boolean isNotBlock = true;
    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = false;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.ROLE_STATION;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}