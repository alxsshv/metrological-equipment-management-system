package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name="Message")
public class MessageDto {
    @XmlElement(name="VerificationMeasuringInstrumentData")
    private MeasuringInstrumentDataDto data;
    @XmlElement(name="SaveMethod")
    private int saveMethod;

    @Override
    public String toString() {
        return "Message{" +
                "data=" + data +
                ", saveMethod=" + saveMethod +
                '}';
    }
}
