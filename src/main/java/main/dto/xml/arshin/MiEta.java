package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name ="mieta")
@XmlAccessorType(XmlAccessType.FIELD)
//Средства измерений, применяемые в качестве эталона
public class MiEta {
    @XmlElement(name ="number")
    private String number;
}
