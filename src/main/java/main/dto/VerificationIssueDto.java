package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VerificationIssueDto {
    private int id;
    private String creationDateTime;
    private List<RecordDto> verificationRecords;

    @Override
    public String  toString() {
        return "VerificationIssueDto{" +
                "id=" + id +
                ", creationDateTime='" + creationDateTime + '\'' +
                ", verificationRecords=" + verificationRecords +
                '}';
    }
}

