package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mi_type_modification")
public class MiTypeModification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "notation")
    private String notation;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "mi_type")
    private MiType miType;
}
