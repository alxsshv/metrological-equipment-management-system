package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class MiInfo {

@XmlElement (name = "singleMI")
private SingleMi singleMi;
@XmlElement
private EtaMi etaMi;

}
