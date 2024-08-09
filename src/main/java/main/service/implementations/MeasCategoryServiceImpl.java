package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MeasCategoryDto;
import main.exception.EntityAlreadyExistException;
import main.model.MeasCategory;
import main.repository.MeasCategoryRepository;
import main.service.interfaces.MeasCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class MeasCategoryServiceImpl implements MeasCategoryService {
    private final MeasCategoryRepository measCategoryRepository;
    private final ModelMapper modelMapper;

    public MeasCategoryServiceImpl(MeasCategoryRepository measCategoryRepository) {
        this.measCategoryRepository = measCategoryRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(@Valid MeasCategoryDto measCategoryDto) {
            validateIfEntityAlreadyExist(measCategoryDto.getTitle());
        MeasCategory measCategory = modelMapper.map(measCategoryDto, MeasCategory.class);
            measCategoryRepository.save(measCategory);
    }

    private void validateIfEntityAlreadyExist(String title) throws EntityAlreadyExistException {
        MeasCategory measCategoryFromDb = measCategoryRepository.findByTitle(title);
        if (measCategoryFromDb != null){
            throw new EntityAlreadyExistException("Данный вид измерений уже занесен в БД");
        }
    }

    @Override
    public MeasCategoryDto findById(long id) {
        MeasCategory measCategory = getById(id);
        return modelMapper.map(measCategory, MeasCategoryDto.class);
    }

    @Override
    public MeasCategory getById(long id) {
        Optional<MeasCategory> measCategoryOpt = measCategoryRepository.findById(id);
        if (measCategoryOpt.isEmpty()) {
            throw new EntityNotFoundException("Запись о запрашиваемом виде измерений не найдена");
        }
        return measCategoryOpt.get();
    }

    @Override
    public Page<MeasCategoryDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title, Pageable pageable) {
            return measCategoryRepository.findByTitleIgnoreCaseContaining(title.trim(), pageable)
                    .map(measCategory -> modelMapper.map(measCategory, MeasCategoryDto.class));
    }

    @Override
    public List<MeasCategoryDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title) {
            return measCategoryRepository.findByTitleIgnoreCaseContaining(title.trim()).stream()
                    .map(measCategory -> modelMapper.map(measCategory, MeasCategoryDto.class)).toList();
    }


    @Override
    public Page<MeasCategoryDto> findAll(Pageable pageable) {
        return measCategoryRepository.findAll(pageable)
                .map(measCategory -> modelMapper.map(measCategory, MeasCategoryDto.class));
    }

    @Override
    public List<MeasCategoryDto> findAll() {
        return measCategoryRepository.findAll().stream()
                .map(measCategory -> modelMapper.map(measCategory, MeasCategoryDto.class)).toList();
    }

    @Override
    public void update(@Valid MeasCategoryDto measCategoryDto){
        MeasCategory measCategory = getById(measCategoryDto.getId());
        MeasCategory updatingMiConditionData = modelMapper.map(measCategory, MeasCategory.class);
        measCategory.setTitle(updatingMiConditionData.getTitle());
        measCategoryRepository.save(measCategory);
    }

    @Override
    public void delete(long id){
        measCategoryRepository.deleteById(id);
    }

}
