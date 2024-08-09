package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.DepartmentDto;
import main.exception.EntityAlreadyExistException;
import main.model.Department;

import main.repository.DepartmentRepository;
import main.service.interfaces.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(@Valid DepartmentDto departmentDto) {
            validateIfEntityAlreadyExist(departmentDto.getNotation());
            Department department = modelMapper.map(departmentDto, Department.class);
            departmentRepository.save(department);
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
    public Page<DepartmentDto> findByNotation(
            @NotBlank(message = "Поле для поиска не может быть пустым") String notation, Pageable pageable) {
            return departmentRepository.findByNotationIgnoreCaseContaining(notation.trim(), pageable)
                    .map(department -> modelMapper.map(department, DepartmentDto.class));
    }

    @Override
    public List<DepartmentDto> findByNotation(
            @NotBlank(message = "Поле для поиска не может быть пустым") String notation) {
            return departmentRepository.findByNotationIgnoreCaseContaining(notation.trim()).stream()
                    .map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
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
    public void update(@Valid DepartmentDto departmentDto){
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
