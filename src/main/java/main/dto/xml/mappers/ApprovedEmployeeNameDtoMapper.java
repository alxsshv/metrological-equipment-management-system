package main.dto.xml.mappers;

import main.dto.xml.fsa.ApprovedEmployeeNameDto;
import main.model.Employee;

public class ApprovedEmployeeNameDtoMapper {
    public static ApprovedEmployeeNameDto mapEmployeeNameToDto(Employee employee){
        ApprovedEmployeeNameDto nameDto = new ApprovedEmployeeNameDto();
        nameDto.setName(employee.getName());
        nameDto.setSurname(employee.getSurname());
        return nameDto;
    }


}
