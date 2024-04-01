package main.dto.verification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
    private long id;
    private String creationDateTime;
    private List<RecordDto> records;
}
