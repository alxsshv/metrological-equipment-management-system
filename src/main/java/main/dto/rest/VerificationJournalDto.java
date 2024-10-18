package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationJournalDto {
    private long id;
    @NotEmpty(message = "Номер журнала не может быть пустым")
    private String number;
    private String title;
    private String description;
    private List<VerificationProtocolDto> protocolDtos = new ArrayList<>();
}
