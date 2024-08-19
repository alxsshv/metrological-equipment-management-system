package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MiStandardDto {
    private long id;
    @NotEmpty(message = "Пожалуйста укажите регистрационный номер эталона в ФГИС\"Аршин\"")
    private String arshinNumber;
    @NotNull(message = "Некорректно указано средство измерений, применяемое в качестве эталона")
    private MiDetailsDto miDetails;
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
                ", miDetails=" + miDetails +
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
