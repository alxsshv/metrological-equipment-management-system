package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
//Средства измерений, применяемые в качестве эталона
public class MiEta {
    private String regNum;
    private String miEtaUrl;
    private String miTypeNumber;
    private String miTypeUrl;
    private String miTypeTitle;
    private String notation;
    private String modification;
    private String manufactureNum;
    @XmlTransient
    private int manufactureYear;
    private String rankCode;
    private String rankTitle;
    private String schemaTitle;
}
