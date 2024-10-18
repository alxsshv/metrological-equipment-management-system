package main.service.implementations;

import main.dto.rest.MiFullDto;
import main.dto.rest.VerificationProtocolDto;
import main.model.MeasurementInstrument;
import main.model.VerificationJournal;
import main.service.interfaces.MeasurementInstrumentService;
import main.service.interfaces.VerificationJournalService;
import main.service.interfaces.VerificationProtocolService;
import main.service.interfaces.VerificationProtocolServiceFacade;
import main.service.utils.fileInfos.ProtocolFileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VerificationProtocolServiceFacadeImpl implements VerificationProtocolServiceFacade {
    private final VerificationProtocolService protocolService;
    private final VerificationJournalService journalService;
    private final MeasurementInstrumentService measurementInstrumentService;


    public VerificationProtocolServiceFacadeImpl(VerificationProtocolService protocolService, VerificationJournalService journalService, MeasurementInstrumentService measurementInstrumentService) {
        this.protocolService = protocolService;
        this.journalService = journalService;
        this.measurementInstrumentService = measurementInstrumentService;
    }

    @Override
    public void upload(MultipartFile file, ProtocolFileInfo protocolFileInfo) throws IOException {
        VerificationJournal journal = journalService.getById(protocolFileInfo.getJournalId());
        MiFullDto instrumentDto = measurementInstrumentService.findById(protocolFileInfo.getCategoryId());
        VerificationProtocolDto protocolDto = new VerificationProtocolDto();
        protocolDto.setNumber(protocolFileInfo.getNumber());
        protocolDto.setDescription(protocolFileInfo.getDescription());
        protocolDto.setInstrumentDto(instrumentDto);
        protocolDto.setVerificationDate(protocolFileInfo.getVerificationDate());
        protocolService.upload(file,protocolDto, journal);
    }


    @Override
    public Page<VerificationProtocolDto> findProtocolsByJournal(long journalId, Pageable pageable) {
        VerificationJournal journal = journalService.getById(journalId);
        return protocolService.findProtocolsByJournal(journal, pageable);
    }

    @Override
    public Page<VerificationProtocolDto> searchJournalProtocols(long journalId, String searchString, Pageable pageable) {
        VerificationJournal journal = journalService.getById(journalId);
        return protocolService.findProtocolsByJournalAndSearchString(journal,searchString, pageable);
    }

    @Override
    public List<VerificationProtocolDto> findProtocols() {
        return protocolService.findProtocols();
    }

    @Override
    public Page<VerificationProtocolDto> findProtocolByMiId(long miId, Pageable pageable) {
        MeasurementInstrument instrument = measurementInstrumentService.getMiById(miId);
        return protocolService.findProtocolByInstrument(instrument,pageable);
    }

    @Override
    public ResponseEntity<?> getProtocolFile(long id){
        return protocolService.getProtocolFile(id);
    }

    @Override
    public void delete(long id) throws IOException {
        protocolService.delete(id);
    }

    @Override
    public void deleteAll(long journal_id) throws IOException {
        VerificationJournal journal = journalService.getById(journal_id);
        protocolService.deleteAll(journal);
    }
}
