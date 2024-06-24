package main.dto.xml.arshin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartyMi extends VerificationObject{
    private String mitypeNumber;
    private String mitypeUrl;
    private String mitypeType;
    private String modification;
    private String quantity;
}
