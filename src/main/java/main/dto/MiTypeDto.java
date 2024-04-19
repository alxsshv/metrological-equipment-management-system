package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class MiTypeDto {
    private long id;
    private String number;
    private String title;
    private String notation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double verificationPeriod;
}
