package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name = "first_name")
    private String fName;

    @Column(name = "last_name")
    private String lName;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @Column(name = "is_block")
    private Boolean isBlock = false;

    @Column(name = "is_verification")
    private Boolean isVerification;

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    private List<CarEntity> cars = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ComplaintUserChargerEntity> chargerComplaints = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ComplaintUserStationEntity> stationComplaints = new ArrayList<>();

    @Builder.Default
    @Column(name = "created_at", updatable = false)
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}
