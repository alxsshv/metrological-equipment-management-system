package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.*;


@XmlTransient
@XmlSeeAlso({SingleMi.class, PartyMi.class, EtaMi.class})
public abstract class VerificationObject {
}
