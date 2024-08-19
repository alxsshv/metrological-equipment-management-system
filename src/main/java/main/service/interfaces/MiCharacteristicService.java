package main.service.interfaces;


import jakarta.validation.Valid;
import main.dto.rest.MiCharacteristicDto;
import main.model.MiCharacteristic;
import main.service.validators.MiCharacteristicAlreadyExist;

public interface MiCharacteristicService {
    void save(@MiCharacteristicAlreadyExist @Valid MiCharacteristicDto miCharacteristicDto);
    MiCharacteristicDto findById(long id);
    MiCharacteristic getById(long id);
    void update(@Valid MiCharacteristicDto miCharacteristicDto);
    void delete(long id);

}
