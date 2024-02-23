package main.service.xml;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.mappers.MeasurementInstrumentDtoMapper;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


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
        deleteEmptyEndVerificationDateTagsFromFile(fsaXML);
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

    private void deleteEmptyEndVerificationDateTagsFromFile(File file) {
        StringBuilder builder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains("<DateEndVerification xsi:nil=\"true\"/>")) {
                    builder.append(line).append("\n");
                }
            }
            scanner.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(builder.toString());
            writer.close();

        } catch (IOException ex) {
            System.out.println("Файл " + filePath + " не найден или не содержит записей");
            System.out.println(ex.getMessage());
        }
    }




}
