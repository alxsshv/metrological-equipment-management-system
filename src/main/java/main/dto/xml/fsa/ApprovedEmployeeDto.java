package main.dto.xml.fsa;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "ApprovedEmployee")
@XmlAccessorType(XmlAccessType.NONE)
public class ApprovedEmployeeDto {
    @XmlElement(name = "Name")
    private ApprovedEmployeeNameDto employeeNameDto;
    @XmlElement(name = "SNILS")
    private String snils;
}


