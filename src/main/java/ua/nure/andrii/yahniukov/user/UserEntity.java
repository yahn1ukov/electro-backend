package ua.nure.andrii.yahniukov.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.complaint.ComplaintUserStationEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.iot.CarEntity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_not_block")
    @Builder.Default
    private Boolean isNotBlock = true;

    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = true;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    private List<CarEntity> cars = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}