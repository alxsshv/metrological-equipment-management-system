package main.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class VerificationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "number_verification")
    private String numberVerification;
    @Column(name = "date_verification")
    private String dateVerification;
    @Column(name = "date_end_verification")
    private String dateEndVerification;
    @Column(name = "type_measuring_instrument")
    private String typeMeasuringInstrument;
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;
    @Column(name = "result_verification")
    private int resultVerification;

    public void updateFrom(VerificationRecord updatingData){
        numberVerification = updatingData.getNumberVerification();
        dateVerification = updatingData.getDateVerification();
        dateEndVerification = updatingData.getDateEndVerification();
        typeMeasuringInstrument = updatingData.getTypeMeasuringInstrument();
        employee = updatingData.getEmployee();
        resultVerification = updatingData.getResultVerification();
    }


}
