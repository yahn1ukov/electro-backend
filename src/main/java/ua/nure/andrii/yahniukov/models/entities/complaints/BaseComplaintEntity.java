package ua.nure.andrii.yahniukov.models.entities.complaints;

import lombok.Builder;
import lombok.Data;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();
}
