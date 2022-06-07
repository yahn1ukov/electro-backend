package ua.nure.andrii.yahniukov.models.entities.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.models.entities.maintenances.StationEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stationUsers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationUserEntity extends BaseUserEntity {
    @Column(name = "company")
    private String company;

    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = false;

    @Builder.Default
    @OneToMany(mappedBy = "owner")
    List<StationEntity> stations = new ArrayList<>();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.SERVICE;

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = null;

    @Builder.Default
    @Column(name = "web_site")
    private String webSite = null;
}
