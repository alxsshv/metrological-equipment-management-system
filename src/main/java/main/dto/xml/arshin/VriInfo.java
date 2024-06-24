package main.dto.xml.arshin;


import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "vriInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class VriInfo {
    @XmlElement(name = "organization")
    private String organization;
    @XmlElement(name = "signCipher")
    private String signCipher;
    @XmlElement(name = "miOwner")
    private String miOwner;
    @XmlElement(name = "vrfDate")
    private String vrfDate;
    @XmlElement(name = "validDate")
    private String validDate;
    @XmlElement(name = "vriType")
    private String vriType;
    private String docTitle;
    @XmlElement(name = "applicable")
    private Applicable applicable;
    @XmlElement(name = "inapplicable")
    private Inapplicable inapplicable;
}