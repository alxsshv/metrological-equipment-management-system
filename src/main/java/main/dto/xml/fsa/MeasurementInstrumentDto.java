package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.Employee;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "VerificationMeasuringInstrument")
public class MeasurementInstrumentDto {
    @XmlElement(name = "NumberVerification")
    private String numberVerification;
    @XmlElement(name = "DateVerification")
    private String dateVerification;
    @XmlElement(name = "DateEndVerification")
    private String dateEndVerification;
    @XmlElement(name = "TypeMeasuringInstrument")
    private String typeMeasuringInstrument;
    @XmlElement(name = "ApprovedEmployee")
    private Employee approvedEmployee;
    @XmlElement(name = "ResultVerification")
    private int resultVerification;

    @Override
    public String toString() {
        return "VerificationMeasurementInstrument{" +
                "numberVerification='" + numberVerification + '\'' +
                ", dateVerification='" + dateVerification + '\'' +
                ", dateEndVerification='" + dateEndVerification + '\'' +
                ", typeMeasuringInstrument='" + typeMeasuringInstrument + '\'' +
                ", approvedEmpolyee='" + approvedEmployee + '\'' +
                ", resultVerification=" + resultVerification +
                '}';
    }
}
