package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "singleMi")
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleMi extends VerificationObject{
    @XmlElement(name = "mitypeNumber")
    private String miTypeNumber;
    private String miTypeUrl;
    private String miTypeType;
    private String miTypeTitle;
    @XmlElement(name = "manufactureNum")
    private String manufactureNum;
    @XmlTransient
    private String inventoryNum;
    @XmlTransient
    private int manufactureYear;
    @XmlElement(name = "modification")
    private String modification;
}
