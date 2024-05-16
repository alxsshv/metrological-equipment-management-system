package main.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.VerificationRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VerificationReportDto {
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<VerificationRecordDto> recordDtos = new ArrayList<>();
}
