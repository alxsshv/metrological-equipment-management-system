package main.dto.xml.arshin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Info {
    private String structure;
    private boolean briefIndicator;
    private String briefCharacteristics;
    private String renges;
    private String values;
    private String channels;
    private String blocks;
    private String url;
    private String additionalInfo;
}
