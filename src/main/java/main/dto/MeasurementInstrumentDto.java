package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MeasurementInstrumentDto {
    private int id;
    private int miTypeId;
    private String modification;
    private String serialNum;
    private String inventoryNum;
    private int manufactureYear;
    private int commissioningYear;
    private String owner;
}
