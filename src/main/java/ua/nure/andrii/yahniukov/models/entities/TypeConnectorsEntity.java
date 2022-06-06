package ua.nure.andrii.yahniukov.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "typeConnectors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeConnectorsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "charger_id", referencedColumnName = "id")
    private ChargerEntity charger;

    @Column(name = "name")
    private String name;
}
