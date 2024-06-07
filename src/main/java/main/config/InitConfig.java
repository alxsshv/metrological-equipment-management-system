package main.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.rest.*;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiTypeRepository;
import main.repository.OrganizationRepository;
import main.service.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class InitConfig {
    @Autowired
    private MiTypeService miTypeService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MeasurementInstrumentService miService;
    @Autowired
    private MiStandardService standardService;
    @Autowired
    private MiTypeRepository miTypeRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private MeasurementInstrumentRepository miRepository;

    public void initiate() throws IOException {

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

        MultipartFile[] files = {};
        String[] descriptions = {};

        miTypeService.save(type1, files, descriptions);
        miTypeService.save(type2, files, descriptions);
        miTypeService.save(type3, files,descriptions);

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

        OrganizationDto organization1 = new OrganizationDto();
        organization1.setTitle("ООО \"Центр метрологии и стандартизации\"");
        organization1.setNotation("ООО \"ЦСМ\"");
        organization1.setAddress("г. Старый Оскол");

        OrganizationDto organization2 = new OrganizationDto();
        organization2.setId(2L);
        organization2.setTitle("АНО \"Лаборатория испытаний\"");
        organization2.setNotation("АНО \"ЛабИ\"");
        organization2.setAddress("г. Нижний Новгород");

        OrganizationDto organization3 = new OrganizationDto();
        organization3.setTitle("ИП \"Степанов Степан Иванович\"");
        organization3.setNotation("ИП \"Степанов\"");
        organization3.setAddress("г. Ростов");

        organizationService.save(organization1);
        organizationService.save(organization2);
        organizationService.save(organization3);

        MiFullDto mi1 = new MiFullDto();
        mi1.setMiType(miTypeRepository.findById(2L).get());
        mi1.setSerialNum("SN1");
        mi1.setModification("ДХС 524-02");
        mi1.setOwner(organizationRepository.findById(2L).get());
        mi1.setApplicable(true);
        mi1.setUser("И.И. Иванов");
        mi1.setVerificationDate(LocalDate.now().toString());
        mi1.setValidDate(LocalDate.now().plusYears(1).toString());
        mi1.setInventoryNum("ИНВ1");

        MiFullDto mi2 = new MiFullDto();
        mi2.setMiType(miTypeRepository.findById(2L).get());
        mi2.setSerialNum("SN2");
        mi2.setModification("ДХС 524-01");
        mi2.setOwner(organizationRepository.findById(2L).get());
        mi2.setApplicable(true);
        mi2.setUser("П.П. Перов");
        mi2.setVerificationDate(LocalDate.now().toString());
        mi2.setValidDate(LocalDate.now().plusYears(1).toString());
        mi2.setInventoryNum("ИНВ2");

        MiFullDto mi3 = new MiFullDto();
        mi3.setMiType(miTypeRepository.findById(2L).get());
        mi3.setSerialNum("SN3");
        mi3.setModification("ДХС 524-03");
        mi3.setOwner(organizationRepository.findById(2L).get());
        mi3.setApplicable(false);
        mi3.setUser("С.С. Сидоров");
        mi3.setVerificationDate(LocalDate.now().toString());
        mi3.setInventoryNum("ИНВ3");

        MiFullDto mi4 = new MiFullDto();
        mi4.setMiType(miTypeRepository.findById(1L).get());
        mi4.setSerialNum("SN004");
        mi4.setModification("СДАИ.406233.106-04");
        mi4.setOwner(organizationRepository.findById(1L).get());
        mi4.setApplicable(true);
        mi4.setUser("И.И. Иванов");
        mi4.setVerificationDate(LocalDate.now().toString());
        mi4.setValidDate(LocalDate.now().plusYears(1).toString());
        mi4.setInventoryNum("ИНВ004");

        MiFullDto mi5 = new MiFullDto();
        mi5.setMiType(miTypeRepository.findById(1L).get());
        mi5.setSerialNum("SN005");
        mi5.setModification("СДАИ.406233.106-05");
        mi5.setOwner(organizationRepository.findById(1L).get());
        mi5.setApplicable(true);
        mi5.setUser("И.И. Ииванов");
        mi5.setVerificationDate(LocalDate.now().toString());
        mi5.setValidDate(LocalDate.now().plusYears(1).toString());
        mi5.setInventoryNum("ИНВ005");

        miService.save(mi1, files, descriptions);
        miService.save(mi2, files, descriptions);
        miService.save(mi3, files, descriptions);
        miService.save(mi4, files, descriptions);
        miService.save(mi5, files, descriptions);

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


    }


}
