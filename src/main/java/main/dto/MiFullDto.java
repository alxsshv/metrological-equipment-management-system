package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.MiType;
import main.model.Organization;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class MiFullDto {
    private int id;
    private MiType miType;
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
    private LocalDateTime creationDateTime;
    private LocalDateTime updatingDateTime;

}