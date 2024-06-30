package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "inapplicable")
@XmlAccessorType(XmlAccessType.FIELD)
public class Inapplicable extends Applicability {
    @XmlElement(name = "reasons")
    private String reasons;
}
