package main.dto.xml.arshin;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "inapplicable")
public class Inapplicable extends Applicability {
    private String reasons;
    private String noticeNum;
}
