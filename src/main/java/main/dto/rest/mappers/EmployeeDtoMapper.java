package main.dto.rest.mappers;

import main.dto.rest.EmployeeDto;
import main.model.Employee;


public class EmployeeDtoMapper {
    public static EmployeeDto mapToDto(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setUserDto(UserDtoMapper.mapToDto(employee.getUser()));
        dto.setSnils(employee.getSnils());
        return dto;
    }
    public static Employee mapToEntity (EmployeeDto dto){
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setUser(UserDtoMapper.mapToEntity(dto.getUserDto()));
        employee.setSnils(dto.getSnils());
        return employee;
    }
}
