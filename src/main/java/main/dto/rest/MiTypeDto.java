package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class MiTypeDto {
    private long id;
    private String number;
    private String title;
    private String notation;
    private String startDate;
    private String endDate;
    private double verificationPeriod;
}
