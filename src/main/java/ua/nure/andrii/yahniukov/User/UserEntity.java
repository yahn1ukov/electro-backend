package ua.nure.andrii.yahniukov.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.ComplaintsUserCharger.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.ComplaintsUserStation.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.IoT.CarEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_not_block")
    @Builder.Default
    private Boolean isNotBlock = true;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = true;

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    private List<CarEntity> cars = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}
