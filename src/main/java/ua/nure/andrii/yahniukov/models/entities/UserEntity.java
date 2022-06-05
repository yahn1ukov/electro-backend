package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.enums.UserRole;

import javax.persistence.*;
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

    @Column(name = "is_block")
    private Boolean isBlock;

    @Column(name = "is_verification")
    private Boolean isVerification;

    @Column(name = "cars")
    @OneToMany(mappedBy = "owner")
    private List<CarEntity> cars;

    @OneToMany(mappedBy = "user")
    private List<ComplaintUserChargerEntity> chargerComplaints;

    @OneToMany(mappedBy = "user")
    private List<ComplaintUserStationEntity> stationComplaints;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
