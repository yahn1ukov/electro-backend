package ua.nure.andrii.yahniukov.chargerUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.charger.ChargerEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "charger_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerUserEntity {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    @Builder.Default
    List<ChargerEntity> chargers = new ArrayList<>();
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
    private UserRole role = UserRole.ROLE_CHARGER;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}