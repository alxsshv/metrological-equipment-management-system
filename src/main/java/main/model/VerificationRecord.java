package main.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "inapplicable_reason", length = 2000)
    private String inapplicableReason;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mi_id")
    private MeasurementInstrument mi;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "verification_records_mi_standards",
            joinColumns = @JoinColumn(name = "verification_record_id"),
            inverseJoinColumns = @JoinColumn(name = "mi_standard_id"))
    private Set<MiStandard> miStandards = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "verification_records_mis",
            joinColumns = @JoinColumn(name = "verification_record_id"),
            inverseJoinColumns = @JoinColumn(name = "mi_id"))
    private Set<MeasurementInstrument> verificationMis = new HashSet<>(); // средства измерений применяемые при поверке
    @Column(name = "calibration")
    private boolean  calibration;
    @Column(name = "sticker_num")
    private String stickerNum;
    @Column(name = "sign_pass")
    private boolean signPass;
    @Column(name = "sign_mi")
    private boolean signMi;
    @Column(name = "short_verification")
    private boolean shortVerification;
    @Column(name = "short_verification_characteristic")
    private String shortVerificationCharacteristic;

    public void updateFrom(VerificationRecord record){
        this.verificationType = record.verificationType;
        this.verificationDate = record.verificationDate;
        this.validDate = record.validDate;
        this.applicable = record.applicable;
        this.temperature = record.temperature;
        this.pressure = record.pressure;
        this.humidity = record.humidity;
        this.mi = record.mi;
        this.employee = record.employee;
        this.miStandards = record.miStandards;
    }

    @Override
    public String toString() {
        return "VerificationRecord{" +
                "id=" + id +
                ", report=" + report +
                ", verificationType=" + verificationType +
                ", verificationDate=" + verificationDate +
                ", validDate=" + validDate +
                ", applicable=" + applicable +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", arshinVerificationNumber='" + arshinVerificationNumber + '\'' +
                ", mi=" + mi +
                ", employee=" + employee +
                ", miStandards=" + miStandards +
                '}';
    }
}
