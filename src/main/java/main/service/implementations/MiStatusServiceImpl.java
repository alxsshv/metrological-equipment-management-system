package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiStatusDto;
import main.model.MiStatus;
import main.repository.MiStatusRepository;
import main.service.interfaces.MiStatusService;
import main.service.validators.MiStatusAlreadyExist;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class MiStatusServiceImpl implements MiStatusService {
    private final MiStatusRepository miStatusRepository;
    private final ModelMapper modelMapper;

    public MiStatusServiceImpl(MiStatusRepository miStatusRepository) {
        this.miStatusRepository = miStatusRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(@MiStatusAlreadyExist @Valid MiStatusDto miStatusDto) {
            MiStatus miStatus = modelMapper.map(miStatusDto, MiStatus.class);
            miStatusRepository.save(miStatus);
    }


    @Override
    public MiStatusDto findById(long id) {
        MiStatus miStatus = getById(id);
        return modelMapper.map(miStatus, MiStatusDto.class);
    }

    @Override
    public MiStatus getById(long id) {
        Optional<MiStatus> miStatusOpt = miStatusRepository.findById(id);
        if (miStatusOpt.isEmpty()) {
            throw new EntityNotFoundException("Запись о статусе СИ не найдена");
        }
        return miStatusOpt.get();
    }

    @Override
    public Page<MiStatusDto> findByStatus(@NotBlank(message = "Поле для поиска не может быть пустым") String status, Pageable pageable) {
            return miStatusRepository.findByStatusIgnoreCaseContaining(status.trim(), pageable)
                    .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class));
    }

    @Override
    public List<MiStatusDto> findByStatus(@NotBlank(message = "Поле для поиска не может быть пустым") String status) {
            return miStatusRepository.findByStatusIgnoreCaseContaining(status.trim()).stream()
                    .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class)).toList();
    }

    @Override
    public Page<MiStatusDto> findAll(Pageable pageable) {
        return miStatusRepository.findAll(pageable)
                .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class));
    }

    @Override
    public List<MiStatusDto> findAll() {
        return miStatusRepository.findAll().stream()
                .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class)).toList();
    }

    @Override
    public void update(@Valid MiStatusDto miStatusDto){
        MiStatus miStatus = getById(miStatusDto.getId());
        MiStatus updatingMiStatusData = modelMapper.map(miStatusDto, MiStatus.class);
        miStatus.setStatus(updatingMiStatusData.getStatus());
        miStatusRepository.save(miStatus);
    }

    @Override
    public void delete(long id){
        miStatusRepository.deleteById(id);
    }

}
