package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
    @XmlElement(name = "miInfo")
    private MiInfo miInfo;
    @XmlElement(name = "vriInfo")
    private VriInfo vriInfo;
    @XmlElement(name = "means")
    private Mean means;
    @XmlElement(name = "conditions")
    private Conditions conditions;
    private Info info;
    private Publication publication;
}
