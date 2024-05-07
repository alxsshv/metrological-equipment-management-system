package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.MiType;
import main.model.Organization;

import java.time.LocalDate;


@NoArgsConstructor
@Setter
@Getter
public class MiFullDto {
    private long id;
    private MiType miType;
    private String title;
    private String modification;
    private String serialNum;
    private String inventoryNum;
    private LocalDate manufactureDate;
    private LocalDate startUseDate;
    private LocalDate verificationDate;
    private LocalDate validDate;
    private boolean applicable;
    private Organization owner;
    private String user;
    private String creationDateTime;
    private String updatingDateTime;

}