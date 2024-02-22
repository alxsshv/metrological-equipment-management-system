package main.dto.arshin_api.verification.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationStatus {
    private int id;
    private ApplicationStatusEnum status;
    //TODO уточнить тип по руководству
    private List<String> verifications;
    private LocalDateTime created;
    private LocalDateTime changed;
    private String regNum;
    private LocalDate regDate;
}
