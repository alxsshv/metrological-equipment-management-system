package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "means")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mean {
    private List<Npe> npeList = new ArrayList<>();
    private List<Uve> uveList = new ArrayList<>();
    private List <Ses> sesList = new ArrayList<>();
    @XmlElements({
            @XmlElement(name = "mieta", type = MiEta.class)
    })
    private List<MiEta> miEtaList = new ArrayList<>();
    private List <Mis> misList = new ArrayList<>();
    private List <Reagent> reagentList = new ArrayList<>();
    private String oMethod;
}
