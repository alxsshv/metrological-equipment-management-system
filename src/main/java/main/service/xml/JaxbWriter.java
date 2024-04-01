package main.service.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import main.dto.xml.fsa.MessageDto;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
@Service
public class JaxbWriter {
    protected String filePath = "/home/alexei/dev/";
    public void write(MessageDto message, long issueNumber){
        try{
            JAXBContext context = JAXBContext.newInstance(MessageDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "schema.xsd");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(message, new FileOutputStream(filePath + "fsaReport" + issueNumber + ".xml"));
            System.out.println("Файл создан");

        } catch (JAXBException e) {
            System.out.println("Jaxb-context ошибочен" + e);
        } catch (FileNotFoundException ex){
            System.out.println("Файл не может быть создан " + ex);
        }
    }
}
