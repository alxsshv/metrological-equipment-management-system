package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class MiDto {
    private long id;
    private String title;
    private String modification;
    private String serialNum;
    private String verificationDate;
    private String validDate;
    private boolean applicable;
    private String owner;
}
