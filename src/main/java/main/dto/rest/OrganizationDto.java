package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrganizationDto {
    private Long id;
    @NotEmpty(message = "Пожалуйста заполните полное наименование организации")
    private String title;
    @NotEmpty(message = "Пожалуйста заполните сокращенное наименование организации")
    private String notation;
    private String address;
    private String creationDateTime;
    private String updatingDateTime;

    @Override
    public String toString() {
        return "OrganizationDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", notation='" + notation + '\'' +
                ", address='" + address + '\'' +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", updatingDateTime='" + updatingDateTime + '\'' +
                '}';
    }
}
