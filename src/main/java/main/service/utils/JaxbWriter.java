package main.service.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.VerificationApplication;
import main.dto.xml.fsa.FsaVerificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
@Component
@Getter
@Setter
public class JaxbWriter {
    public static Logger log = LoggerFactory.getLogger(JaxbWriter.class);
    @Value("${upload.temp.path}")
    private String tempFileUploadPath;
    @Value("${xml.arshin.schema.location}")
    private String xmlSchemaLocation;

    public void writeXMLForArshin(VerificationApplication application, String fileName) throws FileNotFoundException {
        try{
            JAXBContext context = JAXBContext.newInstance(VerificationApplication.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, xmlSchemaLocation);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            createFolderIfNotExist();
            marshaller.marshal(application,new FileOutputStream(tempFileUploadPath + fileName));
        } catch (JAXBException ex) {
            log.error("Jaxb-context ошибочен: " + ex);
        } catch (FileNotFoundException ex){
            log.error("Ошибка создания файла: " + ex);
            throw new FileNotFoundException("Ошибка создания XML файла");
        }
        log.info("Файл создан");
    }

    public void writeXMLForFSA(FsaVerificationMessage fsaVerificationMessage, String fileName) throws FileNotFoundException {
        try{
            JAXBContext context = JAXBContext.newInstance(FsaVerificationMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "schema.xsd");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            createFolderIfNotExist();
            marshaller.marshal(fsaVerificationMessage,new FileOutputStream(tempFileUploadPath + fileName));
        } catch (JAXBException ex) {
            log.error("Jaxb-context ошибочен: " + ex);
        } catch (FileNotFoundException ex){
            log.error("Ошибка создания файла: " + ex);
            throw new FileNotFoundException("Ошибка создания XML файла");
        }
        log.info("Файл создан");
    }

    private void createFolderIfNotExist(){
        File uploadFolder = new File(tempFileUploadPath);
        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
    }
}
