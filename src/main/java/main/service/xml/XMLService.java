package main.service.xml;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.xml.mappers.MeasurementInstrumentDtoMapper;
import main.dto.xml.fsa.MeasurementInstrumentDto;
import main.model.*;
import main.dto.xml.fsa.MessageDto;
import main.dto.xml.fsa.MeasuringInstrumentDataDto;
import main.repository.VerificationIssueRepository;
import main.service.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class XMLService implements IXMLService {

    @Autowired
    private VerificationIssueRepository issueRepository;
    @Value("${upload.path}")
    private String filePath;

    @Override
    public ResponseEntity<?> getFsaXMLForIssue(String id) throws IOException {
        createFsaXMLForVerificationIssue(id);
        File fsaXML = new File(filePath + "fsaReport"+ id +".xml");
        if (!fsaXML.exists()){
            String errorMessage = "Файл " + fsaXML.getName() + " не найден";
            System.out.println(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        String okMessage = "Файл " + fsaXML.getName() + " передан";
        System.out.println(okMessage);
        return ResponseEntity.ok()
                .header("Content-Disposition" , "attachment; filename=\""+ fsaXML.getName() +"\"")
                .body(Files.readAllBytes(Path.of(fsaXML.toURI())));
    }


    private void createFsaXMLForVerificationIssue(String id){
        VerificationIssue verificationIssue = issueRepository.getReferenceById(Integer.valueOf(id));
        MeasuringInstrumentDataDto data = new MeasuringInstrumentDataDto();
        for (VerificationRecord record: verificationIssue.getVerificationRecords()) {
            MeasurementInstrumentDto instrument = MeasurementInstrumentDtoMapper.mapRecordToDto(record);
            data.add(instrument);
        }
        MessageDto message = new MessageDto(data,2);
        JaxbWriter writer = new JaxbWriter();
        writer.write(message, verificationIssue.getId());
    }




}
