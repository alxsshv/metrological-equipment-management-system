package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class MeasurementInstrumentDto {
    private int id;
    private int miTypeId;
    private String modification;
    private String serialNum;
    private String inventoryNum;
    private LocalDate manufactureDate;
    private LocalDate startUseDate;
    private long ownerId;
    private String user;

}
