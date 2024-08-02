package main.service.interfaces;


import main.dto.rest.MiCharacteristicDto;
import main.model.MiCharacteristic;

public interface MiCharacteristicService {
    void save(MiCharacteristicDto miCharacteristicDto);
    MiCharacteristicDto findById(long id);
    MiCharacteristic getById(long id);
    void update(MiCharacteristicDto miCharacteristicDto);
    void delete(long id);

}
