package main.dto.rest;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VerificationReportFullDto {
    private long id;
    private String creationDate;
    private String updateDate;
    private String comment;
    private List<VerificationRecordDto> records = new ArrayList<>();
}
