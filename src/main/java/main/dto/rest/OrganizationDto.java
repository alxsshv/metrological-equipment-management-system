package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrganizationDto {
    private Long id;
    private String title;
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
