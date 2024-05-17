package main.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VerificationReportFullDto {
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String comment;
    private List<VerificationRecordDto> recordDtos = new ArrayList<>();
}
