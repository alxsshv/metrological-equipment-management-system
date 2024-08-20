package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mi_type_modifications")
public class MiTypeModification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "notation")
    private String notation;
    @ManyToOne(fetch = FetchType.LAZY)
    private MiTypeDetails miTypeDetails;

    @Override
    public String toString() {
        return "MiTypeModification{" +
                "id=" + id +
                ", notation='" + notation + '\'' +
                ", miType=" + miTypeDetails.getId() +
                '}';
    }
}
