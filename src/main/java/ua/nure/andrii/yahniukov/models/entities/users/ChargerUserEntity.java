package ua.nure.andrii.yahniukov.models.entities.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.models.entities.maintenances.ChargerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chargerUsers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChargerUserEntity extends BaseUserEntity {
    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    List<ChargerEntity> chargers = new ArrayList<>();
    @Column(name = "company", unique = true)
    private String company;
    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = false;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.CHARGER;

    @Builder.Default
    @Column(name = "phone_number")
    private String phoneNumber = null;

    @Builder.Default
    @Column(name = "web_site")
    private String webSite = null;
}

