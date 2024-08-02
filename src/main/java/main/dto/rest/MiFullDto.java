package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class MiFullDto {
    private long id;
    private MiTypeDto miType;
    private String title;
    private String modification;
    private String serialNum;
    private String inventoryNum;
    private String manufactureDate;
    private String startUseDate;
    private String verificationDate;
    private String validDate;
    private boolean applicable;
    private OrganizationDto owner;
    private String user;
    private String creationDateTime;
    private String updatingDateTime;

    @Override
    public String toString() {
        return "MiFullDto{" +
                "id=" + id +
                ", miType=" + miType +
                ", title='" + title + '\'' +
                ", modification='" + modification + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", inventoryNum='" + inventoryNum + '\'' +
                ", manufactureDate='" + manufactureDate + '\'' +
                ", startUseDate='" + startUseDate + '\'' +
                ", verificationDate='" + verificationDate + '\'' +
                ", validDate='" + validDate + '\'' +
                ", applicable=" + applicable +
                ", owner=" + owner +
                ", user='" + user + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", updatingDateTime='" + updatingDateTime + '\'' +
                '}';
    }
}