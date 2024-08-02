package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MiCharacteristicDto {
    private long id;
    private String title;
    private String value;
    private String unit;
    private MiDetailsDto miDetailsDto;
}
