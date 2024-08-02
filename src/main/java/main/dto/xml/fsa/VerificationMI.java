package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "VerificationMeasuringInstrument")
public class VerificationMI {
    @XmlElement(name = "NumberVerification")
    private String numberVerification;
    @XmlElement(name = "DateVerification")
    private String dateVerification;
    @XmlElement(name = "DateEndVerification")
    private String dateEndVerification;
    @XmlElement(name = "TypeMeasuringInstrument")
    private String typeMeasuringInstrument;
    @XmlElement(name = "ApprovedEmployee")
    private ApprovedEmployee approvedEmployee;
    @XmlElement(name = "ResultVerification")
    private int resultVerification; //  1 - пригоден, 2-непригоден

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
