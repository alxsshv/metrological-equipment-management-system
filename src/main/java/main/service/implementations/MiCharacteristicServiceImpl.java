package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import main.dto.rest.MiCharacteristicDto;
import main.model.MiCharacteristic;
import main.repository.MiCharacteristicRepository;
import main.service.interfaces.MiCharacteristicService;
import main.service.validators.MiCharacteristicAlreadyExist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


@Service
@Validated
@AllArgsConstructor
public class MiCharacteristicServiceImpl implements MiCharacteristicService {
    private final MiCharacteristicRepository miCharacteristicRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public void save(@MiCharacteristicAlreadyExist @Valid MiCharacteristicDto miCharacteristicDto) {
            MiCharacteristic miCharacteristic = modelMapper.map(miCharacteristicDto, MiCharacteristic.class);
            miCharacteristicRepository.save(miCharacteristic);
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

    @Override
    public void update(@Valid MiCharacteristicDto miCharacteristicDto){
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
