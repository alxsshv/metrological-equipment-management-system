package main.model.verification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.Employee;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "arshin_record_number")
    private String arshinRecordNumber;
    @Column(name = "mi_type_number")
    private String miTypeNumber;
    @Column(name = "modification")
    private String modification;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "mi_owner")
    private String miOwner;
    @Column(name = "verification_date")
    private String verificationDate;
    @Column(name = "valid_date")
    private String validDate;
    @Column(name = "result")
    private boolean result;
    @Column(name = "verification_type")
    private int verificationType;
    @Column(name = "calibration")
    private boolean calibration;
    @Column(name = "sticker_number")
    private String stickerNumber;
    @Column(name = "procedure_doc_number")
    private String procedureDocNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;
    @Column(name = "standard")
    private String standard;
    @Column(name = "temperature")
    private double temperature; // Celseus
    @Column(name = "pressure")
    private double pressure; // kPa
    @Column(name = "humidity")
    private double humidity; // percents
    @Column (name = "note")
    private String note;

    public void updateFrom (Record updatingData){
        arshinRecordNumber = updatingData.getArshinRecordNumber();
        miTypeNumber = updatingData.getMiTypeNumber();
        modification = updatingData.getModification();
        serialNumber = updatingData.getSerialNumber();
        miOwner = updatingData.getMiOwner();
        verificationDate = updatingData.getVerificationDate();
        validDate = updatingData.getValidDate();
        result = updatingData.isResult();
        verificationType = updatingData.getVerificationType();
        calibration = updatingData.isCalibration();
        stickerNumber = updatingData.getStickerNumber();
        procedureDocNumber = updatingData.getProcedureDocNumber();
        employee = updatingData.getEmployee();
        standard = updatingData.getStandard();
        temperature = updatingData.getTemperature();
        pressure = updatingData.getPressure();
        humidity = updatingData.getHumidity();
        note = updatingData.getNote();
    }
}
