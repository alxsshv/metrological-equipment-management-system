package main.dto.rest;

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
    private boolean readyToSend;
    private boolean sentToArshin;
    private boolean sentToFsa;
}
