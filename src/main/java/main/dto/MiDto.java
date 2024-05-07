package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Setter
@Getter
public class MiDto {
    private long id;
    private String title;
    private String modification;
    private String serialNum;
    private LocalDate verificationDate;
    private LocalDate validDate;
    private boolean applicable;
    private String owner;
}
