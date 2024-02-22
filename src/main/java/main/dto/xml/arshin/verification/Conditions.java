package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement (name = "conditions")
public class Conditions {
    @XmlElement
    private String temperature;
    @XmlElement
    private String pressure;
    @XmlElement (name = "hymidity")
    private String humidity;
}
