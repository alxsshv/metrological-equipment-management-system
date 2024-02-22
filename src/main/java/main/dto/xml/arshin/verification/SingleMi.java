package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlType(name = "singleMI", propOrder = {"mitypeNumber", "manufactureNum", "modification"})
public class SingleMi {
    @XmlElement
    private String mitypeNumber; // Номер типа СИ
    @XmlElement
    private String modification;
    @XmlElement
    private String manufactureNum; // Заводской номер
}
