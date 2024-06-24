package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "ApprovedEmployee")
@XmlAccessorType(XmlAccessType.NONE)
public class ApprovedEmployee {
    @XmlElement(name = "Name")
    private EmployeeName employeeName;
    @XmlElement(name = "SNILS")
    private String snils;
}
