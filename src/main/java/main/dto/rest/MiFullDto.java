package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class MiFullDto {
    private long id;
    @NotNull(message = "Пожалуйста укажите тип средства измерений")
    private MiTypeDto miType;
    private String title;
    @NotEmpty(message = "Некорректно указана модификация средства измерений")
    private String modification;
    @NotEmpty(message = "Пожалуйста заполните заводской номер средства измерений")
    private String serialNum;
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