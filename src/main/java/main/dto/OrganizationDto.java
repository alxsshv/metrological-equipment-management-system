package main.dto;

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
}
