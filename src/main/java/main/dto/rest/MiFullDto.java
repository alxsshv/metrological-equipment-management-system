package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.MiType;
import main.model.Organization;


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
    private String manufactureDate;
    private String startUseDate;
    private String verificationDate;
    private String validDate;
    private boolean applicable;
    private Organization owner;
    private String user;
    private String creationDateTime;
    private String updatingDateTime;

}