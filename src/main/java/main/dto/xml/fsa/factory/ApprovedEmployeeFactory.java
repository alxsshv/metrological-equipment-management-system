package main.dto.xml.fsa.factory;

import main.dto.xml.fsa.ApprovedEmployee;
import main.dto.xml.fsa.EmployeeName;
import main.model.Employee;

public class ApprovedEmployeeFactory {
    public static ApprovedEmployee createApprovedEmployee(Employee employee){
        EmployeeName employeeName = new EmployeeName();
        employeeName.setName(employee.getName());
        employeeName.setSurname(employee.getSurname());

        ApprovedEmployee approvedEmployee = new ApprovedEmployee();
        approvedEmployee.setSnils(employee.getSnils());
        approvedEmployee.setEmployeeName(employeeName);
        return approvedEmployee;
    }
}
