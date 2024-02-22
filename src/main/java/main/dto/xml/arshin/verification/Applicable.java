package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Applicable {
    @XmlElement
    private String stickerNum; //№ наклейки
    @XmlElement
    private boolean signPass;
    @XmlElement
    private boolean signMi;

}
