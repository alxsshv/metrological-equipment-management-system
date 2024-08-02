package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mi_characteristic")
public class MiCharacteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "value")
    private String value;
    @Column(name = "unit")
    private String unit;
    @ManyToOne(fetch = FetchType.LAZY)
    private MiDetails miDetails;

    public void updateFrom(MiCharacteristic updateData){
        this.title = updateData.getTitle();
        this.value = updateData.getValue();
        this.unit = updateData.getUnit();
    }
}
