package main.service.interfaces;

import main.dto.rest.VerificationProtocolDto;
import main.model.VerificationProtocol;
import main.service.utils.fileInfos.ProtocolFileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VerificationProtocolServiceFacade {
    void upload(MultipartFile file, ProtocolFileInfo protocolFileInfo) throws IOException;
    Page<VerificationProtocolDto> findProtocolsByJournal(long journalId, Pageable pageable);
    Page<VerificationProtocolDto> findJournalProtocolsBySearchString(long journalId, String searchString, Pageable pageable);
    List<VerificationProtocolDto> findProtocols();
    Page<VerificationProtocolDto> findProtocolByMiId(long miId, Pageable pageable);
    Page<VerificationProtocolDto> findProtocolByMiIdAndSearchString(long miId, String searchString, Pageable pageable);
    VerificationProtocol getProtocolById(long id);
    ResponseEntity<?> getProtocolFile(long id);
    void delete(long id) throws IOException;
    void deleteAll(long journalId) throws IOException;

}

