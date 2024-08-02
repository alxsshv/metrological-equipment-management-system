package main.dto.rest;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiDetailsDto {
    private long id;
    private String inventoryNum;
    private String miRegistryNumber;
    private LocalDate manufactureDate;
    private LocalDate startUseDate;
    private MiConditionDto condition;
    private MiStatusDto status;
    private DepartmentDto department;
    private MeasCategoryDto measCategory;
    private List<MiCharacteristic> characteristics;
    private String user;
    private MiFullDto miFullDto;

    @Override
    public String toString() {
        return "MiDetailsDto{" +
                "id=" + id +
                ", inventoryNum='" + inventoryNum + '\'' +
                ", miRegistryNumber='" + miRegistryNumber + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", startUseDate=" + startUseDate +
                ", condition=" + condition +
                ", status=" + status +
                ", department=" + department +
                ", measCategory=" + measCategory +
                ", characteristics=" + characteristics +
                ", user='" + user + '\'' +
                ", miFullDto=" + miFullDto +
                '}';
    }
}
