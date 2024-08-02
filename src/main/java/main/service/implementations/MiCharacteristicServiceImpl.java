package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiCharacteristicDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.model.MiCharacteristic;
import main.model.MiDetails;
import main.repository.MiCharacteristicRepository;
import main.service.interfaces.MiCharacteristicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MiCharacteristicServiceImpl implements MiCharacteristicService {
    private final MiCharacteristicRepository miCharacteristicRepository;
    private final ModelMapper modelMapper;

    public MiCharacteristicServiceImpl(MiCharacteristicRepository miCharacteristicRepository) {
        this.miCharacteristicRepository = miCharacteristicRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(MiCharacteristicDto miCharacteristicDto) {
            checkMiCharacteristicDtoComposition(miCharacteristicDto);
            validateIfEntityAlreadyExist(miCharacteristicDto);
            MiCharacteristic miCharacteristic = modelMapper.map(miCharacteristicDto, MiCharacteristic.class);
            miCharacteristicRepository.save(miCharacteristic);
    }

    private void checkMiCharacteristicDtoComposition(MiCharacteristicDto dto) throws DtoCompositionException {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните наименование характеристики");
        }
        if (dto.getValue() == null || dto.getValue().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните значение характеристики");
        }
        if (dto.getUnit() == null || dto.getUnit().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните единицу измерения");
        }
        if (dto.getMiDetailsDto() == null) {
            throw new DtoCompositionException("Для вносимой характеристики не указано средство измерений");
        }
    }

    @Override
    public MiCharacteristicDto findById(long id) {
            MiCharacteristic miCharacteristic = getById(id);
            return modelMapper.map(miCharacteristic, MiCharacteristicDto.class);
    }

    public MiCharacteristic getById(long id) {
        Optional<MiCharacteristic> miCharacteristicOpt = miCharacteristicRepository.findById(id);
        if (miCharacteristicOpt.isEmpty()) {
            throw new EntityNotFoundException("Характеристика с id " + id + " не найдена");
        }
        return miCharacteristicOpt.get();
    }


    private void validateIfEntityAlreadyExist(MiCharacteristicDto miCharacteristicDto) throws EntityAlreadyExistException {
        MiCharacteristic miCharacteristicFromDb = miCharacteristicRepository.findByTitleAndMiDetails(miCharacteristicDto.getTitle(), modelMapper.map(miCharacteristicDto.getMiDetailsDto(), MiDetails.class));
        if (miCharacteristicFromDb != null){
            throw new EntityAlreadyExistException("Характеристика с таким наименованием для данного СИ уже добавлена");
        }
    }

    @Override
    public void update(MiCharacteristicDto miCharacteristicDto){
        checkMiCharacteristicDtoComposition(miCharacteristicDto);
        MiCharacteristic miCharacteristic = getById(miCharacteristicDto.getId());
        MiCharacteristic updatingMiCharacteristicData = modelMapper.map(miCharacteristicDto, MiCharacteristic.class);
        miCharacteristic.updateFrom(updatingMiCharacteristicData);
        miCharacteristicRepository.save(miCharacteristic);
    }

    @Override
    public void delete(long id){
        miCharacteristicRepository.deleteById(id);
    }

}
