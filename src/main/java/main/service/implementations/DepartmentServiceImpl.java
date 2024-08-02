package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;

import main.dto.rest.DepartmentDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.Department;

import main.repository.DepartmentRepository;
import main.service.interfaces.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(DepartmentDto departmentDto) {
        checkDepartmentDtoComposition(departmentDto);
            validateIfEntityAlreadyExist(departmentDto.getNotation());
            Department department = modelMapper.map(departmentDto, Department.class);
            departmentRepository.save(department);
    }

    private void checkDepartmentDtoComposition(DepartmentDto dto) throws DtoCompositionException {
        if (dto.getNotation() == null || dto.getNotation().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните обозначение подразделения");
        }
    }

    @Override
    public DepartmentDto findById(long id) {
            Department department = getById(id);
            return modelMapper.map(department, DepartmentDto.class);
    }

    public Department getById(long id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);
        if (departmentOpt.isEmpty()) {
            throw new EntityNotFoundException("Запись о подразделении с id " + id + " не найдена");
        }
        return departmentOpt.get();
    }


    private void validateIfEntityAlreadyExist(String notation) throws EntityAlreadyExistException {
        Department departmentFromDb = departmentRepository.findByNotation(notation);
        if (departmentFromDb != null){
            throw new EntityAlreadyExistException("Запись о подразделении уже существует");
        }
    }

    @Override
    public Page<DepartmentDto> findByNotation(String notation, Pageable pageable) {
            validateSearchString(notation);
            return departmentRepository.findByNotationIgnoreCaseContaining(notation.trim(), pageable)
                    .map(department -> modelMapper.map(department, DepartmentDto.class));
    }

    @Override
    public List<DepartmentDto> findByNotation(String notation) {
            validateSearchString(notation);
            return departmentRepository.findByNotationIgnoreCaseContaining(notation.trim()).stream()
                    .map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()){
            throw  new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }

    @Override
    public Page<DepartmentDto> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(department -> modelMapper.map(department, DepartmentDto.class));
    }

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
    }

    @Override
    public void update(DepartmentDto departmentDto){
        checkDepartmentDtoComposition(departmentDto);
        Department department = getById(departmentDto.getId());
        Department updatingDepartmentData = modelMapper.map(departmentDto, Department.class);
        department.setNotation(updatingDepartmentData.getNotation());
        departmentRepository.save(department);
    }

    @Override
    public void delete(long id){
        departmentRepository.deleteById(id);
    }

}
