package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiStatusDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.MiStatus;
import main.repository.MiStatusRepository;
import main.service.interfaces.MiStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MiStatusServiceImpl implements MiStatusService {
    private final MiStatusRepository miStatusRepository;
    private final ModelMapper modelMapper;

    public MiStatusServiceImpl(MiStatusRepository miStatusRepository) {
        this.miStatusRepository = miStatusRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(MiStatusDto miStatusDto) {
            checkMiStatusDtoComposition(miStatusDto);
            validateIfEntityAlreadyExist(miStatusDto.getStatus());
            MiStatus miStatus = modelMapper.map(miStatusDto, MiStatus.class);
            miStatusRepository.save(miStatus);
    }

    private void checkMiStatusDtoComposition(MiStatusDto dto) throws DtoCompositionException {
        if (dto.getStatus() == null || dto.getStatus().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните наименование статуса СИ");
        }
    }

    private void validateIfEntityAlreadyExist(String status) throws EntityAlreadyExistException {
        MiStatus miStatusFromDb = miStatusRepository.findByStatus(status);
        if (miStatusFromDb != null){
            throw new EntityAlreadyExistException("Статус СИ уже существует");
        }
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
    public Page<MiStatusDto> findByStatus(String status, Pageable pageable) {
            validateSearchString(status);
            return miStatusRepository.findByStatusIgnoreCaseContaining(status.trim(), pageable)
                    .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class));
    }

    @Override
    public List<MiStatusDto> findByStatus(String status) {
            validateSearchString(status);
            return miStatusRepository.findByStatusIgnoreCaseContaining(status.trim()).stream()
                    .map(miStatus -> modelMapper.map(miStatus, MiStatusDto.class)).toList();
    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()){
            throw  new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
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
    public void update(MiStatusDto miStatusDto){
        checkMiStatusDtoComposition(miStatusDto);
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
