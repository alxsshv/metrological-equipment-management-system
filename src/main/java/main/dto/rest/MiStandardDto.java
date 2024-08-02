package main.dto.rest;

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
    private String schemaNotation;
    private String stateStandardNumber;
    private String level;
    private String creationDateTime;
    private String updatingDateTime;

    @Override
    public String toString() {
        return "MiStandardDto{" +
                "id=" + id +
                ", arshinNumber='" + arshinNumber + '\'' +
                ", measurementInstrument=" + measurementInstrument +
                ", schemaType='" + schemaType + '\'' +
                ", schemaTitle='" + schemaTitle + '\'' +
                ", stateStandardNumber='" + stateStandardNumber + '\'' +
                ", schemaNotation='" + schemaNotation + '\'' +
                ", level='" + level + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", updatingDateTime='" + updatingDateTime + '\'' +
                '}';
    }
}
