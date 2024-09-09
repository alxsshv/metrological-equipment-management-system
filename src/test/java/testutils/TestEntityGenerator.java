package testutils;

import main.dto.rest.*;
import main.dto.rest.mappers.*;
import main.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TestEntityGenerator {
    public static OrganizationDto generateOrganisationDto(Long id){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(id);
        organizationDto.setNotation("АО\"Организация\"");
        organizationDto.setTitle("Акционерное общество \"Организация\"");
        organizationDto.setAddress("г. Санкт-Петербург");
        organizationDto.setCreationDateTime(LocalDateTime.now().toString());
        return organizationDto;
    }

    public static Organization generateOrganisation(Long id){
        return OrganizationDtoMapper.mapToEntity(generateOrganisationDto(id));
    }

    public static MiTypeFullDto generateMiTypeFullDto(Long id){
        MiTypeFullDto miTypeFullDto = new MiTypeFullDto();
        miTypeFullDto.setId(id);
        miTypeFullDto.setNumber("12345-12");
        miTypeFullDto.setTitle("Вольтметры");
        miTypeFullDto.setNotation("В7-78");
        miTypeFullDto.setVerificationPeriod(12);
        miTypeFullDto.setInstructionTitle("Методика поверки");
        miTypeFullDto.setInstructionNotation("В7-78МП");
        List<String> modifications = List.of("В7-78/1","В7-78/2","В7-78/3","В7-78/4");
        miTypeFullDto.setModifications(modifications);
        return miTypeFullDto;
    }

    public static MiTypeInstruction generateMiTypeInstruction(Long id){
        return MiTypeDtoMapper.mapToEntity(generateMiTypeFullDto(id));
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

    public static Employee generateEmployee(long id){
        return EmployeeDtoMapper.mapToEntity(generateEmployeeDto(id));
    }

    public static MiFullDto generateMeasurementInstrumentFullDto(Long id) {
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(id);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN"+id);
        miFullDto.setOwner(generateOrganisation(1L));
        miFullDto.setMiType(generateMiTypeInstruction(1L).getMiType());
        miFullDto.setUser("User");
        miFullDto.setVerificationDate(LocalDate.now().toString());
        return miFullDto;
    }

    public static MeasurementInstrument generateMeasurementInstrument(long id){
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(generateMeasurementInstrumentFullDto(id));
        mi.setCreationDateTime(LocalDateTime.now());
        return mi;
    }

    public static MiStandardDto generateMiStandardDto(long id){
        MiStandardDto standardDto = new MiStandardDto();
        standardDto.setId(id);
        standardDto.setMeasurementInstrument(generateMeasurementInstrument(id));
        standardDto.setArshinNumber("1111.1Р.1111");
        standardDto.setSchemaTitle("Государственная поверочная схема акустического давления");
        standardDto.setSchemaNotation("Государственная поверочная схема акустического давления");
        standardDto.setLevel("1P");
        return standardDto;
    }

    public static MiStandard generateMiStandard(long id){
        return MiStandardDtoMapper.mapToEntity(generateMiStandardDto(id));
    }

    public static VerificationRecordDto generateVerificationRecordDto(long id){
        VerificationRecordDto recordDto = new VerificationRecordDto();
        recordDto.setMi(generateMeasurementInstrument(1L));
        recordDto.setVerificationMis(List.of(generateMeasurementInstrument(2L), generateMeasurementInstrument(3L)));
        recordDto.setId(id);
        recordDto.setArshinVerificationNumber("1111111");
        recordDto.setEmployee(generateEmployee(1L));
        recordDto.setApplicable(true);
        recordDto.setVerificationDate(String.valueOf(LocalDate.now()));
        recordDto.setValidDate(String.valueOf(LocalDate.now().plusYears(2)));
        recordDto.setMiStandards(List.of(generateMiStandard(1L)));
        recordDto.setHumidity(45);
        recordDto.setPressure(100);
        recordDto.setTemperature(25);
        recordDto.setShortVerification(false);
        recordDto.setCalibration(false);
        recordDto.setStickerNum("отсутствует");
        return recordDto;
    }

    public static VerificationRecord genereteVerificationRecord(long id){
        return VerificationRecordDtoMapper.mapToEntity(generateVerificationRecordDto(id));
    }


}
