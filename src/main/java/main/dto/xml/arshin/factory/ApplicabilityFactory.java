package main.dto.xml.arshin.factory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.Applicable;
import main.dto.xml.arshin.Inapplicable;
import main.model.VerificationRecord;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Getter
@Setter
public class ApplicabilityFactory {
    public Applicable createApplicable(){
            Applicable applicable = new Applicable();
            applicable.setSignPass(false);
            applicable.setSignMi(false);
            return applicable;
    }
    public Inapplicable createInapplicable(VerificationRecord record){
        Inapplicable inapplicable = new Inapplicable();
        inapplicable.setReasons(record.getInapplicableReason());
        return inapplicable;
    }
}
