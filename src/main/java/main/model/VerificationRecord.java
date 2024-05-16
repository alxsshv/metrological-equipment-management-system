package main.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "verification_records")
public class VerificationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private VerificationReport report;
    @Column(name = "verification_type")
    private int verificationType; // 1 - первичная, 2 - периодическая
    @Column(name = "verification_date")
    private LocalDate verificationDate;
    @Column(name = "valid_date")
    private LocalDate validDate;
    @Column(name = "applicable")
    private boolean applicable;
    @Column(name = "temperature")
    private double temperature; //deg. Celseus
    @Column(name = "pressure")
    private double pressure; //kPa
    @Column(name = "humidity")
    private double humidity; // percents
    @Column(name = "arshin_verification_number")
    private String arshinVerificationNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mi_id")
    private MeasurementInstrument mi;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mi_standard_id")
    private List<MiStandard> miStandards = new ArrayList<>();
}
