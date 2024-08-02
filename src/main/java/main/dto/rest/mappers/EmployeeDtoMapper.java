package main.dto.rest.mappers;

import main.dto.rest.EmployeeDto;
import main.model.Employee;


public class EmployeeDtoMapper {
    public static EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPatronymic(employee.getPatronymic());
        dto.setSurname(employee.getSurname());
        dto.setSnils(employee.getSnils());
        return dto;
    }
    public static Employee mapToEntity (EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setPatronymic(dto.getPatronymic());
        employee.setSnils(dto.getSnils());
        return employee;
    }
}
