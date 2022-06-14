package ua.nure.andrii.yahniukov.StationUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.andrii.yahniukov.Station.StationEntity;
import ua.nure.andrii.yahniukov.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stationUsers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationUserEntity {
    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    List<StationEntity> stations = new ArrayList<>();
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

    @Column(name = "company", unique = true)
    private String company;

    @Column(name = "is_verification")
    @Builder.Default
    private Boolean isVerification = false;

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

    @Column(name = "created_at", updatable = false)
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}