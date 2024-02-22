package main.dto.xml.fsa.mappers;

import main.dto.xml.fsa.ApprovedEmployeeDto;
import main.model.Employee;

public class ApprovedEmployeeDtoMappers {

    public static ApprovedEmployeeDto mapEmployeeToDto (Employee employee){
        ApprovedEmployeeDto approvedEmployeeDto = new ApprovedEmployeeDto();
        approvedEmployeeDto
                .setEmployeeNameDto(ApprovedEmployeeNameDtoMapper.mapEmployeeNameToDto(employee));
        approvedEmployeeDto.setSnils(employee.getSnils());
        return approvedEmployeeDto;
    }

}
