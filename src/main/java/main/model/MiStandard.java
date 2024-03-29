package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mi_standards")
public class MiStandard {
    // Средства измерений, применяемые в качестве эталона
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "arshin_number")
    private String arshinNumber; // номер эталона, присвоенный организацией-владельцем
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @Column(name = "measurement_instrument")
    private MeasurementInstrument measurementInstrument; // Средство измерения, являющееся эталоном
    @Column(name = "schema_type")
    private String schemaType; // локальная или государственная поверочная схема
    @Column(name = "schema_title")
    private String schemaTitle;
    @Column(name = "state_standard_num")
    private String stateStandardNumber; // номер государственного поверочного
    @Column(name = "level_code")
    private String levelCode; // код рязряда по поверочой схеме
    @Column(name= "leve_title")
    private String levelTitle; // разряд по поверочной схеме.
}
