package main.config;

import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import main.dto.rest.*;
import main.exception.EntityAlreadyExistException;
import main.model.*;
import main.service.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.multipart.MultipartFile;


@Configuration
@NoArgsConstructor
@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class InitConfig {
    @Autowired
    private MiTypeService miTypeService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MiDetailsService miService;
    @Autowired
    private MiStandardService standardService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MeasCategoryService measCategoryService;
    @Autowired
    private MiConditionService miConditionService;
    @Autowired
    private MiStatusService miStatusService;
    private ModelMapper modelMapper = new ModelMapper();
    private MultipartFile[] files = {};
    private String[] descriptions = {};
    @Value("${arshin.verification.uri}")
    private String uriBase;



    @PostConstruct
    public void initiate(){
        generateDefaultOrganization();
        generateDefaultDepartment();
        generateDefaultMeasCategory();
        generateDefaultMiConditions();
        generateDefaultMiStatus();
    }

    private void generateDefaultOrganization(){
        String value = "Не указан";
        OrganizationDto defaultOrganizationDto = new OrganizationDto();
        try {
                defaultOrganizationDto.setTitle(value);
                defaultOrganizationDto.setNotation(value);
                defaultOrganizationDto.setAddress(value);
                organizationService.save(defaultOrganizationDto);
            } catch (EntityAlreadyExistException ex){
                log.warn(ex.getMessage());
            }
        defaultOrganizationDto = organizationService.findBySearchString(value).get(0);
        Organization organization = modelMapper.map(defaultOrganizationDto, Organization.class);
        AppDefaults.setDefaultOrganization(organization);
    }

    private void generateDefaultDepartment(){
        DepartmentDto defaultDepartmentDto = new DepartmentDto();
        try {
            defaultDepartmentDto.setNotation("не указано");
            departmentService.save(defaultDepartmentDto);

        } catch (EntityAlreadyExistException ex){
            log.warn(ex.getMessage());
        }
        defaultDepartmentDto = departmentService.findByNotation("Не указано").get(0);
        AppDefaults.setDefaultDepartment(modelMapper.map(defaultDepartmentDto, Department.class));
    }

    private void generateDefaultMeasCategory(){
        MeasCategoryDto defaultMeasCategoryDto = new MeasCategoryDto();
        try{
        defaultMeasCategoryDto.setNumber(0);
        defaultMeasCategoryDto.setTitle("не указан");
        measCategoryService.save(defaultMeasCategoryDto);
        } catch (EntityAlreadyExistException ex){
            log.warn(ex.getMessage());
        }
        defaultMeasCategoryDto = measCategoryService.findByTitle("Не указан").get(0);
        AppDefaults.setDefaultMeasCategory(modelMapper.map(defaultMeasCategoryDto, MeasCategory.class));
    }

    private void generateDefaultMiConditions(){
        MiConditionDto defaultMiConditionDto1 = new MiConditionDto();
        try{
            defaultMiConditionDto1.setTitle("В работе");
            miConditionService.save(defaultMiConditionDto1);
            MiConditionDto miConditionDto2 = new MiConditionDto();
            miConditionDto2.setTitle("На хранении");
            miConditionService.save(miConditionDto2);
            MiConditionDto miConditionDto3 = new MiConditionDto();
            miConditionDto3.setTitle("В ремонте");
            miConditionService.save(miConditionDto3);
        } catch (EntityAlreadyExistException ex){
            log.warn(ex.getMessage());
        }
        MiConditionDto defaultMiConditionDto = miConditionService.findByTitle("В работе").get(0);
        AppDefaults.setDefaultMiCondition(modelMapper.map(defaultMiConditionDto, MiCondition.class));
    }

    private void generateDefaultMiStatus(){
        MiStatusDto defaultMiStatusDto1 = new MiStatusDto();
        try {
            defaultMiStatusDto1.setStatus("Рабочее СИ");
            miStatusService.save(defaultMiStatusDto1);
            MiStatusDto defaultMiStatusDto2 = new MiStatusDto();
            defaultMiStatusDto2.setStatus("Эталон");
            miStatusService.save(defaultMiStatusDto2);
            MiStatusDto defaultMiStatusDto3 = new MiStatusDto();
            defaultMiStatusDto3.setStatus("Индикатор");
            miStatusService.save(defaultMiStatusDto3);
        } catch (EntityAlreadyExistException ex){
            log.warn(ex.getMessage());
        }
        MiStatusDto workingToolMiStatusDto =  miStatusService.findByStatus("Рабочее СИ").get(0);
        AppDefaults.setWorkingToolMiStatus(modelMapper.map(workingToolMiStatusDto, MiStatus.class));
        MiStatusDto standardMiStatusDto =  miStatusService.findByStatus("Эталон").get(0);
        AppDefaults.setStandardMiStatus(modelMapper.map(standardMiStatusDto, MiStatus.class));
        MiStatusDto indicatorMiStatusDto =  miStatusService.findByStatus("Индикатор").get(0);
        AppDefaults.setIndicatorMiStatus(modelMapper.map(indicatorMiStatusDto, MiStatus.class));
    }





}
