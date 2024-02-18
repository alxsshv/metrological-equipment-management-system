package main.dto.mappers;

import main.dto.EmployeeDto;
import main.model.Employee;
import main.model.EmployeeName;

public class EmployeeDtoMapper {
    public static EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getEmployeeName().getName());
        dto.setPatronymic(employee.getEmployeeName().getPatronymic());
        dto.setSurname(employee.getEmployeeName().getSurname());
        dto.setSnils(employee.getSnils());
        return dto;
    }
    public static Employee mapToEntity (EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setEmployeeName(
                new EmployeeName(employee.getId(), dto.getSurname(), dto.getName(), dto.getPatronymic()));
        employee.setSnils(dto.getSnils());
        return employee;
    }
}
