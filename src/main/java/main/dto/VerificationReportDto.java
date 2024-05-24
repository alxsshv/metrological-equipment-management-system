package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class VerificationReportDto {
    private long id;
    private String creationDate;
    private String updateDate;
    private String comment;
}
