package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@NoArgsConstructor
@XmlRootElement (name = "VerificationMeasuringInstrumentData")
public class VerificationMiData {
    @XmlElement(name = "VerificationMeasuringInstrument")
    private ArrayList<VerificationMI> instruments  = new ArrayList<>();

    public void add(VerificationMI instrument){
        instruments.add(instrument);
    }

    @Override
    public String toString() {
        return "MeasuringInstrumentDataDto{" +
                "instruments=" + instruments +
                '}';
    }
}