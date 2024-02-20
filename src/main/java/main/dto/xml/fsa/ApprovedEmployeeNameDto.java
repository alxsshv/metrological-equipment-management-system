package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "Name")
@XmlAccessorType(XmlAccessType.NONE)
public class ApprovedEmployeeNameDto {
    @XmlElement(name = "Last")
    private String surname;
    @XmlElement(name = "First")
    private String name;

}
