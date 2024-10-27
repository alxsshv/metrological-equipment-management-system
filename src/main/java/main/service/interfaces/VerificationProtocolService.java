package main.service.interfaces;

import main.dto.rest.VerificationProtocolDto;
import main.model.MeasurementInstrument;
import main.model.VerificationJournal;
import main.model.VerificationProtocol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface VerificationProtocolService {
    void upload(MultipartFile file, VerificationProtocolDto protocolDto, VerificationJournal journal) throws IOException;
    List<VerificationProtocolDto> findProtocols();
    Page<VerificationProtocolDto> findProtocolsByJournal(VerificationJournal journal, Pageable pageable);
    Page<VerificationProtocolDto> findProtocolsByJournalAndSearchString(VerificationJournal journal,String searchString, Pageable pageable);
    Page<VerificationProtocolDto> findProtocolByInstrument(MeasurementInstrument instrument, Pageable pageable);
    VerificationProtocol getProtocolById(long id);
    ResponseEntity<?> getProtocolFile(long id);
    void update(VerificationProtocol updateData);
    void delete(long id) throws IOException;
    void deleteAll(VerificationJournal journal) throws IOException;
}
