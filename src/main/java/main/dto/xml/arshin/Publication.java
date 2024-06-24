package main.dto.xml.arshin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Publication {
    private String status;
    private String reason;
    private LocalDate date;
}
