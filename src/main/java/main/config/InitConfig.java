package main.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.EmployeeDto;
import main.dto.MiTypeFullDto;
import main.dto.OrganizationDto;
import main.service.implementations.EmployeeService;
import main.service.implementations.MiTypeService;
import main.service.implementations.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void initiate() throws IOException {

        MiTypeFullDto type1 = new MiTypeFullDto();
        type1.setNumber("88363-23");
        type1.setTitle("Датчики давления");
        type1.setNotation("Вm 212А.4");
        type1.setStartDate(LocalDate.parse("2023-03-01"));
        type1.setEndDate(LocalDate.parse("2028-03-01"));
        type1.setVerificationPeriod(3);
        type1.setModifications(List.of("СДАИ.406233.106","СДАИ.406233.106-01","СДАИ.406233.106-02",
                        "СДАИ.406233.106-03","СДАИ.406233.106-04","СДАИ.406233.106-05","СДАИ.406233.106-06","СДАИ.406233.106-07",
                        "СДАИ.406233.106-08","СДАИ.406233.106-09","СДАИ.406233.106-10","СДАИ.406233.106-11","СДАИ.406233.106-12",
                        "СДАИ.406233.106-13","СДАИ.406233.106-14","СДАИ.406233.106-15","СДАИ.406233.106-16","СДАИ.406233.106-17",
                        "СДАИ.406233.106-18","СДАИ.406233.106-19","СДАИ.406233.106-20","СДАИ.406233.106-21","СДАИ.406233.106-22",
                        "СДАИ.406233.106-23"));
        type1.setInstructionNotation("СДАИ.406233.106МП");
        type1.setInstructionTitle("Датчики ддавления Вт 212А.4. Методика поверки");

        MiTypeFullDto type2 = new MiTypeFullDto();
               type2.setNumber("76077-19");
               type2.setTitle("Датчики давления");
               type2.setNotation("ДХС 524");
               type2.setStartDate(LocalDate.parse("2019-11-08"));
               type2.setEndDate(LocalDate.parse("2024-11-08"));
               type2.setVerificationPeriod(2);
               type2.setModifications(List.of("ДХС 524","ДХС 524-01","ДХС 524-02","ДХС 524-03","ДХС 524-04","ДХС 524-05",
                       "ДХС 524-06","ДХС 524-07"));
               type2.setInstructionTitle("Датчики давления ДХС 524. Методика поверки");
               type2.setInstructionNotation("СДАИ.404233.016МП");

        MiTypeFullDto type3 = new MiTypeFullDto();
        type3.setNumber("57671-14");
        type3.setTitle("Датчики давления");
        type3.setNotation("Bm 206");
        type3.setStartDate(LocalDate.parse("2019-07-04"));
        type3.setEndDate(LocalDate.parse("2024-05-17"));
        type3.setVerificationPeriod(2);
        type3.setModifications(List.of("Bm 206","Bm 206.1A-31","Вт 206 I-14","Вт 206-03"));
        type3.setInstructionTitle("Датчики давления Вт 206. Методика поверки");
        type3.setInstructionNotation("СДАИ.413455.045МП");

        miTypeService.save(type1, null);
        miTypeService.save(type2, null);
        miTypeService.save(type3, null);

        EmployeeDto employee1 = new EmployeeDto();
        employee1.setName("Иван");
        employee1.setPatronymic("Иванович");
        employee1.setSurname("Иванов");
        employee1.setSnils("11111111111");

        EmployeeDto employee2 = new EmployeeDto();
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

    }


}
