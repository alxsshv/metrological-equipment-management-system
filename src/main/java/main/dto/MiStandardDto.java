package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.MeasurementInstrument;


@Getter
@Setter
@NoArgsConstructor
public class MiStandardDto {
    private long id;
    private String arshinNumber;
    private MeasurementInstrument measurementInstrument;
    private String schemaType;
    private String schemaTitle;
    private String stateStandardNumber;
    private String levelCode;
    private String levelTitle;
    private String creationDateTime;
    private String updatingDateTime;
}
