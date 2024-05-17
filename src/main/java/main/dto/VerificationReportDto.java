package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class VerificationReportDto {
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String comment;
}