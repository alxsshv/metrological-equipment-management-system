package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.config.AppUploadPaths;
import main.dto.rest.VerificationProtocolDto;
import main.dto.rest.mappers.VerificationProtocolDtoMapper;
import main.model.MeasurementInstrument;
import main.model.VerificationJournal;
import main.model.VerificationProtocol;
import main.repository.VerificationProtocolRepository;
import main.service.interfaces.VerificationProtocolService;
import main.service.utils.FileContentTypeBuilder;
import main.service.utils.PathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class VerificationProtocolServiceImpl implements VerificationProtocolService {
    @Autowired
    private AppUploadPaths appUploadPaths;
    @Autowired
    private VerificationProtocolRepository protocolRepository;
    @Autowired
    private PathResolver pathResolver;

    @Override
    public void upload(MultipartFile file, VerificationProtocolDto protocolDto, VerificationJournal journal) throws IOException {
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        String storageFilename = UUID.randomUUID()+ "." + filename;
        pathResolver.createFilePathIfNotExist(appUploadPaths.getOriginVerificationProtocolsPath());
        VerificationProtocol protocol = VerificationProtocolDtoMapper.map(protocolDto);
        protocol.setJournal(journal);
        protocol.setOriginalFilename(filename);
        protocol.setExtension(extension);
        protocol.setStorageFileName(storageFilename);
        protocol.setAwaitingSigning(true);
        protocol.setSigned(false);
        file.transferTo(new File(appUploadPaths.getOriginVerificationProtocolsPath()+ "/" + storageFilename));
        protocolRepository.save(protocol);
        log.info("Протокол поверки \"{}\" успешно загружен на сервер", filename);
    }


    @Override
    public List<VerificationProtocolDto> findProtocols() {
        return protocolRepository.findAll().stream().map(VerificationProtocolDtoMapper::map).toList();
    }

    @Override
    public Page<VerificationProtocolDto> findProtocolsByJournal(VerificationJournal journal, Pageable pageable) {
        return protocolRepository.findByJournal(journal,pageable).map(VerificationProtocolDtoMapper::map);
    }

    @Override
    public Page<VerificationProtocolDto> findProtocolsByJournalAndSearchString(VerificationJournal journal, String searchString, Pageable pageable) {
        return protocolRepository.findByJournalAndNumberIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(journal,searchString,searchString,pageable)
                .map(VerificationProtocolDtoMapper::map);
    }

    @Override
    public Page<VerificationProtocolDto> findProtocolByInstrument(MeasurementInstrument instrument, Pageable pageable) {
        return protocolRepository.findByInstrument(instrument, pageable).map(VerificationProtocolDtoMapper::map);
    }


    @Override
    public VerificationProtocol getProtocolById(long id) {
        Optional<VerificationProtocol> protocolOpt =  protocolRepository.findById(id);
        if (protocolOpt.isEmpty()){
            throw new EntityNotFoundException("Протокол поверки не найден");
        }
        return protocolOpt.get();
    }

    @Override
    public ResponseEntity<?> getProtocolFile(long id) {
        VerificationProtocol protocol =getProtocolById(id);
        try {
            return buildResponseEntity(protocol);
        } catch (IOException ex){
            log.error("Запрашиваемый файл не найден или повержден");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/file_not_found");
            return new ResponseEntity<>(null,headers, HttpStatus.FOUND);
        }
    }

    @Override
    public void update(VerificationProtocol updateData) {
        VerificationProtocol protocol = getProtocolById(updateData.getId());
        protocol.updateFrom(updateData);
        protocolRepository.save(protocol);
    }

    private ResponseEntity<?> buildResponseEntity(VerificationProtocol protocol) throws IOException {
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(protocol.getOriginalFilename(), StandardCharsets.UTF_8).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(FileContentTypeBuilder.getContentType(protocol.getExtension()));
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(Files.readAllBytes(Path
                        .of(resolveFilePath(protocol)))));
    }

    private String resolveFilePath(VerificationProtocol protocol){
       if (protocol.isSigned()){
           return  appUploadPaths.getSignedVerificationProtocolsPath() + "/" + protocol.getSignedFileName();
       }
       return appUploadPaths.getOriginVerificationProtocolsPath() + "/" + protocol.getStorageFileName();
    }

    @Override
    public void delete(long id) throws IOException {
        VerificationProtocol protocol = getProtocolById(id);
        try {
            Files.deleteIfExists(Path.of(appUploadPaths.getSignedVerificationProtocolsPath() + "/" + protocol.getSignedFileName()));
            Files.deleteIfExists(Path.of(appUploadPaths.getOriginVerificationProtocolsPath() + "/" + protocol.getOriginalFilename()));
            protocolRepository.deleteById(id);
        } catch (IOException ex){
            String errorMessage = "Ошибка удаления файла";
            log.info(errorMessage + ":" + ex.getMessage());
            throw new IOException(errorMessage);
        }
    }

    @Override
    public void deleteAll(VerificationJournal journal) throws IOException {
        List<VerificationProtocol> protocols = protocolRepository.findByJournal(journal);
        for (VerificationProtocol protocol : protocols){
            delete(protocol.getId());
        }
    }
}
