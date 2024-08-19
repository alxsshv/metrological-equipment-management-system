package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.mappers.MeasurementInstrumentMapper;
import main.model.MeasurementInstrument;
import main.repository.MeasurementInstrumentRepository;
import main.service.interfaces.MeasurementInstrumentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Validated
public class MiServiceImpl implements MeasurementInstrumentService {
    public static final Logger log = LoggerFactory.getLogger(MiServiceImpl.class);
    private final MeasurementInstrumentRepository measurementInstrumentRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MiFullDto findById(long id) {
        MeasurementInstrument instrument = getMiById(id);
       return modelMapper.map(instrument, MiFullDto.class);
    }

    public MeasurementInstrument getMiById(long id){
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isEmpty()){
            throw new EntityNotFoundException("Средство измерений № "+ id +" не найдено");
        }
        return instrumentOpt.get();
    }

    @Override
    public Page<MiDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable) {
            return measurementInstrumentRepository
                    .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContaining(searchString.trim(), searchString.trim(), pageable)
                    .map(MeasurementInstrumentMapper::mapToDto);
    }

    @Override
    public List<MiDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString) {
            return measurementInstrumentRepository
                    .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContaining(searchString.trim(), searchString.trim())
                    .stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }


    @Override
    public Page<MiDto> findAll(Pageable pageable) {
        return measurementInstrumentRepository.findAll(pageable).map(MeasurementInstrumentMapper::mapToDto);
    }

    @Override
    public List<MiDto> findAll() {
        return measurementInstrumentRepository.findAll().stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }


}
