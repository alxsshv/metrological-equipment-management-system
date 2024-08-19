package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiConditionDto;
import main.model.MiCondition;
import main.repository.MiConditionRepository;
import main.service.interfaces.MiConditionService;
import main.service.validators.MiConditionAlreadyExist;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class MiConditionServiceImpl implements MiConditionService {
    private final MiConditionRepository miConditionRepository;
    private final ModelMapper modelMapper;

    public MiConditionServiceImpl(MiConditionRepository miConditionRepository) {
        this.miConditionRepository = miConditionRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(@MiConditionAlreadyExist @Valid MiConditionDto miConditionDto) {
            MiCondition miCondition = modelMapper.map(miConditionDto, MiCondition.class);
            miConditionRepository.save(miCondition);
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
    public Page<MiConditionDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title, Pageable pageable) {
            return miConditionRepository.findByTitleIgnoreCaseContaining(title.trim(), pageable)
                    .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class));
    }

    @Override
    public List<MiConditionDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title) {
            return miConditionRepository.findByTitleIgnoreCaseContaining(title.trim()).stream()
                    .map(miCondition -> modelMapper.map(miCondition, MiConditionDto.class)).toList();
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
    public void update(@Valid MiConditionDto miConditionDto){
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
