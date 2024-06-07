package testutils;

import main.dto.rest.EmployeeDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.MiTypeFullDto;
import main.dto.rest.OrganizationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDtoGenerator {
    public static OrganizationDto generateOrganisationDtoWithId(Long id){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(id);
        organizationDto.setNotation("АО\"Организация\"");
        organizationDto.setTitle("Акционерное общество \"Организация\"");
        organizationDto.setAddress("г. Санкт-Петербург");
        organizationDto.setCreationDateTime(LocalDateTime.now().toString());
        return organizationDto;
    }

    public static MiTypeFullDto generateMiTypeFullDtoWithId(Long id){
        MiTypeFullDto miTypeFullDto = new MiTypeFullDto();
        miTypeFullDto.setId(id);
        miTypeFullDto.setNumber("12345-12");
        miTypeFullDto.setTitle("Вольтметры");
        miTypeFullDto.setNotation("В7-78");
        miTypeFullDto.setVerificationPeriod(12);
        miTypeFullDto.setInstructionTitle("Методика поверки");
        miTypeFullDto.setInstructionNotation("В7-78МП");
        List<String> modifications = new ArrayList<>();
        modifications.add("В7-78/1");
        modifications.add("В7-78/2");
        miTypeFullDto.setModifications(modifications);
        return miTypeFullDto;
    }
    public static EmployeeDto generateEmployeeDto(Long id) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);
        employeeDto.setName("Ivan");
        employeeDto.setSurname("Ivanov");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("12345678910");
        return employeeDto;
    }

    public static MiFullDto generateMeasurementInstrumentFullDto(Long id) {
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(id);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN"+id);
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        return miFullDto;
    }
}
