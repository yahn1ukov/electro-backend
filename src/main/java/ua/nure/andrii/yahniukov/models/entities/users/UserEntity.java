package ua.nure.andrii.yahniukov.models.entities.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.models.entities.IoT.CarEntity;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserChargerEntity;
import ua.nure.andrii.yahniukov.models.entities.complaints.ComplaintUserStationEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseUserEntity {
    @Column(name = "first_name")
    private String fName;

    @Column(name = "last_name")
    private String lName;

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
}
