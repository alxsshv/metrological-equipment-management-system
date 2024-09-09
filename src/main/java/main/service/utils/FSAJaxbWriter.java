package main.service.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.config.AppUploadPaths;
import main.dto.xml.fsa.FsaVerificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Component
@Getter
@Setter
@Slf4j
public class FSAJaxbWriter extends JaxbWriter {
    @Autowired
    private AppUploadPaths appUploadPaths;

    public void writeXMLFile(FsaVerificationMessage fsaVerificationMessage, String fileName) throws FileNotFoundException {
        try{
            JAXBContext context = JAXBContext.newInstance(FsaVerificationMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "schema.xsd");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            createFolderIfNotExist();
            marshaller.marshal(fsaVerificationMessage,new FileOutputStream(appUploadPaths.getReportsPath() + fileName));
        } catch (JAXBException ex) {
            log.error("Jaxb-context ошибочен: " + ex);
        } catch (FileNotFoundException ex){
            log.error("Ошибка создания файла: " + ex);
            throw new FileNotFoundException("Ошибка создания XML файла");
        }
        log.info("Файл {} создан", fileName);
    }

}
