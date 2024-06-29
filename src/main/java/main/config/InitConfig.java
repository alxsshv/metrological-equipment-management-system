package main.config;

import jakarta.annotation.PostConstruct;
import lombok.*;
import main.dto.rest.*;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiTypeRepository;
import main.service.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InitConfig {
    @Autowired
    private MiTypeServiceImpl miTypeService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private OrganizationServiceImpl organizationService;
    @Autowired
    private MiServiceImpl miService;
    @Autowired
    private MiStandardServiceImpl standardService;
    @Autowired
    private MiTypeRepository miTypeRepository;
    @Autowired
    private MeasurementInstrumentRepository miRepository;
    private MultipartFile[] files = {};
    private String[] descriptions = {};
    @Value("${arshin.verification.uri}")
    private String uriBase;



    @PostConstruct
    public void initiate() throws IOException {
        OrganizationDto organization1 = new OrganizationDto();
        organization1.setTitle("Не указан");
        organization1.setNotation("Не указан");
        organization1.setAddress("Не указан");
        organizationService.save(organization1);
        generateDemoEntities();
    }


     public void generateDemoEntities() throws IOException {
        createMiTypes();
        createEmployee();
        createOrganization();
        createMi();
        createStandards();
    }

    private void createMiTypes() throws IOException {
        MiTypeFullDto type1 = new MiTypeFullDto();
        type1.setNumber("88363-23");
        type1.setTitle("Датчики давления");
        type1.setNotation("Вm 212А.4");
        type1.setMiTitleTemplate("Датчик давления");
        type1.setStartDate("2023-03-01");
        type1.setEndDate("2028-03-01");
        type1.setVerificationPeriod(3);
        type1.setModifications(List.of("СДАИ.406233.106","СДАИ.406233.106-01","СДАИ.406233.106-02",
                "СДАИ.406233.106-03","СДАИ.406233.106-04","СДАИ.406233.106-05","СДАИ.406233.106-06","СДАИ.406233.106-07",
                "СДАИ.406233.106-08","СДАИ.406233.106-09","СДАИ.406233.106-10","СДАИ.406233.106-11","СДАИ.406233.106-12",
                "СДАИ.406233.106-13","СДАИ.406233.106-14","СДАИ.406233.106-15","СДАИ.406233.106-16","СДАИ.406233.106-17",
                "СДАИ.406233.106-18","СДАИ.406233.106-19","СДАИ.406233.106-20","СДАИ.406233.106-21","СДАИ.406233.106-22",
                "СДАИ.406233.106-23"));
        type1.setInstructionNotation("СДАИ.406233.106МП");
        type1.setInstructionTitle("Датчики давления Вт 212А.4. Методика поверки");

        MiTypeFullDto type2 = new MiTypeFullDto();
        type2.setNumber("76077-19");
        type2.setTitle("Датчики давления");
        type2.setNotation("ДХС 524");
        type2.setMiTitleTemplate("Датчик давления");
        type2.setStartDate("2019-11-08");
        type2.setEndDate("2024-11-08");
        type2.setVerificationPeriod(2);
        type2.setModifications(List.of("ДХС 524","ДХС 524-01","ДХС 524-02","ДХС 524-03","ДХС 524-04","ДХС 524-05",
                "ДХС 524-06","ДХС 524-07"));
        type2.setInstructionTitle("Датчики давления ДХС 524. Методика поверки");
        type2.setInstructionNotation("СДАИ.404233.016МП");

        MiTypeFullDto type3 = new MiTypeFullDto();
        type3.setNumber("57671-14");
        type3.setTitle("Датчики давления");
        type3.setNotation("Bm 206");
        type3.setStartDate("2019-07-04");
        type3.setEndDate("2024-05-17");
        type3.setVerificationPeriod(2);
        type3.setModifications(List.of("Bm 206","Bm 206.1A-31","Вт 206 I-14","Вт 206-03"));
        type3.setInstructionTitle("Датчики давления Вт 206. Методика поверки");
        type3.setInstructionNotation("СДАИ.413455.045МП");

        MiTypeFullDto type4 = new MiTypeFullDto();
        type4.setNumber("3383-72");
        type4.setTitle("Манометры технические показывающие в корпусе диаметром 60 мм");
        type4.setNotation("МТ");
        type4.setStartDate("1972-01-01");
        type4.setEndDate(LocalDate.now().toString());
        type4.setVerificationPeriod(10);
        type4.setModifications(List.of("МТ"));
        type4.setInstructionTitle("ГОСТ 8.002-71");
        type4.setInstructionNotation("ГОСТ 8.002-71");

        MiTypeFullDto type5 = new MiTypeFullDto();
        type5.setNumber("32108-06");
        type5.setTitle("Штангенциркули");
        type5.setNotation("ШЦ, ШЦЦ, ШЦК");
        type5.setStartDate("2006-07-01");
        type5.setEndDate("2011-07-01");
        type5.setVerificationPeriod(1);
        type5.setModifications(List.of("ШЦ","ШЦЦ","ШЦК"));
        type5.setInstructionTitle("Государственная система обеспечения единства измерений. Штангенциркули. Методика поверки");
        type5.setInstructionNotation("ГОСТ 8.113-85");

        miTypeService.save(type1, files, descriptions);
        miTypeService.save(type2, files, descriptions);
        miTypeService.save(type3, files,descriptions);
        miTypeService.save(type4, files,descriptions);
        miTypeService.save(type5, files,descriptions);
        System.out.println("Типы СИ созданы");
    }

    private void createEmployee(){
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setId(1L);
        employee1.setName("Иван");
        employee1.setPatronymic("Иванович");
        employee1.setSurname("Иванов");
        employee1.setSnils("11111111111");

        EmployeeDto employee2 = new EmployeeDto();
        employee2.setId(2L);
        employee2.setName("Сергей");
        employee2.setPatronymic("Сергеевич");
        employee2.setSurname("Сергеев");
        employee2.setSnils("11111111112");

        EmployeeDto employee3 = new EmployeeDto();
        employee3.setName("Петр");
        employee3.setPatronymic("Петрович");
        employee3.setSurname("Петров");
        employee3.setSnils("11111111113");

        employeeService.save(employee1);
        employeeService.save(employee2);
        employeeService.save(employee3);
        System.out.println("Employee сгенерированы");
    }

    private void createOrganization(){
        OrganizationDto organization1 = new OrganizationDto();
        organization1.setId(2L);
        organization1.setTitle("ООО \"Центр метрологии и стандартизации\"");
        organization1.setNotation("ООО \"ЦСМ\"");
        organization1.setAddress("г. Старый Оскол");

        OrganizationDto organization2 = new OrganizationDto();
        organization2.setId(3L);
        organization2.setTitle("АНО \"Лаборатория испытаний\"");
        organization2.setNotation("АНО \"ЛабИ\"");
        organization2.setAddress("г. Нижний Новгород");

        OrganizationDto organization3 = new OrganizationDto();
        organization3.setId(4L);
        organization3.setTitle("ИП \"Степанов Степан Иванович\"");
        organization3.setNotation("ИП \"Степанов\"");
        organization3.setAddress("г. Ростов");

        organizationService.save(organization1);
        organizationService.save(organization2);
        organizationService.save(organization3);
        System.out.println("Организации сгенерированы");
    }

    private void createMi() throws IOException {
        MiFullDto mi1 = new MiFullDto();
        mi1.setMiType(miTypeRepository.findById(2L).get());
        mi1.setSerialNum("SN1");
        mi1.setModification("ДХС 524-02");
        mi1.setOwner(organizationService.getOrganizationById(1L));
        mi1.setApplicable(true);
        mi1.setUser("И.И. Иванов");
        mi1.setVerificationDate(LocalDate.now().toString());
        mi1.setValidDate(LocalDate.now().plusYears(1).toString());
        mi1.setInventoryNum("ИНВ1");

        MiFullDto mi2 = new MiFullDto();
        mi2.setMiType(miTypeRepository.findById(2L).get());
        mi2.setSerialNum("SN2");
        mi2.setModification("ДХС 524-01");
        mi2.setOwner(organizationService.getOrganizationById(2L));
        mi2.setApplicable(true);
        mi2.setUser("П.П. Перов");
        mi2.setVerificationDate(LocalDate.now().toString());
        mi2.setValidDate(LocalDate.now().plusYears(1).toString());
        mi2.setInventoryNum("ИНВ2");

        MiFullDto mi3 = new MiFullDto();
        mi3.setMiType(miTypeRepository.findById(2L).get());
        mi3.setSerialNum("SN3");
        mi3.setModification("ДХС 524-03");
        mi3.setOwner(organizationService.getOrganizationById(2L));
        mi3.setApplicable(false);
        mi3.setUser("С.С. Сидоров");
        mi3.setVerificationDate(LocalDate.now().toString());
        mi3.setInventoryNum("ИНВ3");

        MiFullDto mi4 = new MiFullDto();
        mi4.setMiType(miTypeRepository.findById(1L).get());
        mi4.setSerialNum("SN004");
        mi4.setModification("СДАИ.406233.106-04");
        mi4.setOwner(organizationService.getOrganizationById(1L));
        mi4.setApplicable(true);
        mi4.setUser("И.И. Иванов");
        mi4.setVerificationDate(LocalDate.now().toString());
        mi4.setValidDate(LocalDate.now().plusYears(1).toString());
        mi4.setInventoryNum("ИНВ004");

        MiFullDto mi5 = new MiFullDto();
        mi5.setMiType(miTypeRepository.findById(1L).get());
        mi5.setSerialNum("SN005");
        mi5.setModification("СДАИ.406233.106-05");
        mi5.setOwner(organizationService.getOrganizationById(1L));
        mi5.setApplicable(true);
        mi5.setUser("И.И. Ииванов");
        mi5.setVerificationDate(LocalDate.now().toString());
        mi5.setValidDate(LocalDate.now().plusYears(1).toString());
        mi5.setInventoryNum("ИНВ005");

        MiFullDto mi6 = new MiFullDto();
        mi6.setMiType(miTypeRepository.findById(4L).get());
        mi6.setSerialNum("ꓲꓦ-79");
        mi6.setModification("МТ");
        mi6.setOwner(organizationService.getOrganizationById(1L));
        mi6.setApplicable(true);
        mi6.setUser("И.И. Ииванов");
        mi6.setVerificationDate("2024-05-20");
        mi6.setValidDate("2025-05-19");
        mi6.setInventoryNum("");

        MiFullDto mi7 = new MiFullDto();
        mi7.setMiType(miTypeRepository.findById(5L).get());
        mi7.setSerialNum("Я 07920");
        mi7.setModification("ШЦ");
        mi7.setOwner(organizationService.getOrganizationById(1L));
        mi7.setApplicable(true);
        mi7.setUser("И.И. Ииванов");
        mi7.setVerificationDate("2024-03-15");
        mi7.setValidDate("2025-03-14");
        mi7.setInventoryNum("");

        miService.save(mi1, files, descriptions);
        miService.save(mi2, files, descriptions);
        miService.save(mi3, files, descriptions);
        miService.save(mi4, files, descriptions);
        miService.save(mi5, files, descriptions);
        miService.save(mi6, files, descriptions);
        miService.save(mi7, files, descriptions);

        System.out.println("СИ сгенерированы");
    }

    private void createStandards() throws IOException {
        MiStandardDto standard1 = new MiStandardDto();
        standard1.setArshinNumber("1111.1Р.1111");
        standard1.setMeasurementInstrument(miRepository.findById(1L).get());
        standard1.setSchemaTitle("Государственная поверочная схема акустического давления");
        standard1.setSchemaNotation("Государственная поверочная схема акустического давления");
        standard1.setLevel("1P");

        MiStandardDto standard2 = new MiStandardDto();
        standard2.setArshinNumber("2222.2Р.2222");
        standard2.setMeasurementInstrument(miRepository.findById(4L).get());
        standard2.setSchemaTitle("Государственная поверочная схема избыточного давления");
        standard2.setSchemaNotation("Государственная поверочная схема избыточного давления");
        standard2.setLevel("2P");

        standardService.save(standard1, files, descriptions);
        standardService.save(standard2, files, descriptions);
        System.out.println("Стандарты сгенерированы");
    }



}
