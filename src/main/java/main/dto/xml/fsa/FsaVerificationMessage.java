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
public class FsaVerificationMessage {
    @XmlElement(name="VerificationMeasuringInstrumentData")
    private VerificationMiData data;
    @XmlElement(name="SaveMethod")
    private int saveMethod; // 1 - черновик, 2- отправлено
}
