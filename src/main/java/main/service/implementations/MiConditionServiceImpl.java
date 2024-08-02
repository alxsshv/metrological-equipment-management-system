package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiConditionDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.MiCondition;
import main.repository.MiConditionRepository;
import main.service.interfaces.MiConditionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MiConditionServiceImpl implements MiConditionService {
    private final MiConditionRepository miConditionRepository;
    private final ModelMapper modelMapper;

    public MiConditionServiceImpl(MiConditionRepository miConditionRepository) {
        this.miConditionRepository = miConditionRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(MiConditionDto miConditionDto) {
            checkMiConditionDtoComposition(miConditionDto);
            validateIfEntityAlreadyExist(miConditionDto.getTitle());
            MiCondition miCondition = modelMapper.map(miConditionDto, MiCondition.class);
            miConditionRepository.save(miCondition);
    }

    private void checkMiConditionDtoComposition(MiConditionDto dto) throws DtoCompositionException {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните наименование состояния СИ");
        }
    }

    private void validateIfEntityAlreadyExist(String title) throws EntityAlreadyExistException {
        MiCondition miConditionFromDb = miConditionRepository.findByTitle(title);
        if (miConditionFromDb != null){
            throw new EntityAlreadyExistException("Состояние СИ уже существует");
        }
    }

    @Override
    public MiConditionDto findById(long id) {
        MiCondition miCondition = getById(id);
        return modelMapper.map(miCondition, MiConditionDto.class);
    }

    @Override
    public MiCondition getById(long id) {
        Optional<MiCondition> miConditionOpt = miConditionRepository.findById(id);
        if (miConditionOpt.isEmpty()) {
            throw new EntityNotFoundException("Запись о состоянии СИ не найдена");
        }
        return miConditionOpt.get();
    }

    @Override
    public Page<MiConditionDto> findByTitle(String title, Pageable pageable) {
            validateSearchString(title);
            return miConditionRepository.findByTitleIgnoreCaseContaining(title.trim(), pageable)
                    .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class));
    }

    @Override
    public List<MiConditionDto> findByTitle(String title) {
            validateSearchString(title);
            return miConditionRepository.findByTitleIgnoreCaseContaining(title.trim()).stream()
                    .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class)).toList();
    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()){
            throw  new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }

    @Override
    public Page<MiConditionDto> findAll(Pageable pageable) {
        return miConditionRepository.findAll(pageable)
                .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class));
    }

    @Override
    public List<MiConditionDto> findAll() {
        return miConditionRepository.findAll().stream()
                .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class)).toList();
    }

    @Override
    public void update(MiConditionDto miConditionDto){
        checkMiConditionDtoComposition(miConditionDto);
        MiCondition miCondition = getById(miConditionDto.getId());
        MiCondition updatingMiConditionData = modelMapper.map(miConditionDto, MiCondition.class);
        miCondition.setTitle(updatingMiConditionData.getTitle());
        miConditionRepository.save(miCondition);
    }

    @Override
    public void delete(long id){
        miConditionRepository.deleteById(id);
    }

}
