package testutils;

import main.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class TestEntityGenerator {
    public static MeasurementInstrument generateMeasurementInstrumentWithId(Long id){
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setId(id);
        mi.setMiType(generateMiTypeWithId(1L));
        mi.setUser("User");
        mi.setCreationDateTime(LocalDateTime.now());
        mi.setApplicable(true);
        mi.setSerialNum("SN"+id);
        mi.setOwner(generateOrganizationWithId(1L));
        return mi;
    }

    public static MiType generateMiTypeWithId(Long id){
        MiType miType = new MiType();
        miType.setId(id);
        miType.setNumber("12345-78");
        miType.setTitle("Вольтметры");
        miType.setNotation("В7-78");
        miType.setVerificationPeriod(12);
        MiTypeModification modification = new MiTypeModification();
        modification.setId(id);
        modification.setNotation("В7-78/1");
        modification.setMiType(miType);
        miType.setModifications(List.of(modification));
        return miType;
    }

    public static Organization generateOrganizationWithId(Long id){
        Organization organization = new Organization();
        organization.setId(id);
        organization.setNotation("АО\"Организация\"");
        organization.setTitle("Акционерное общество \"Организация\"");
        organization.setAddress("г. Екатеринбург");
        organization.setCreationDateTime(LocalDateTime.now());
        return organization;
    }

    public static MiTypeInstruction generateMiTypeInstructionWithId(Long id){
        MiTypeInstruction instruction = new MiTypeInstruction();
        MiType miType = generateMiTypeWithId(id);
        instruction.setMiType(miType);
        instruction.setId(id);
        return instruction;
    }

    public static Employee generateEmployeeWithId(Long id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Ivan");
        employee.setSurname("Ivanov");
        employee.setPatronymic("Ivanovich");
        employee.setSnils("11111111111");
        return employee;
    }

}
