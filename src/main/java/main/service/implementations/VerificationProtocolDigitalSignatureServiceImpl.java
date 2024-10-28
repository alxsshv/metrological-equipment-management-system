package main.service.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.config.AppUploadPaths;
import main.model.User;
import main.model.VerificationProtocol;
import main.service.interfaces.DigitalSignatureService;
import main.service.interfaces.VerificationProtocolService;
import main.service.utils.PDFEditor;
import main.service.utils.PathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

@Service
@AllArgsConstructor
@Getter
@Setter
public class VerificationProtocolDigitalSignatureServiceImpl implements DigitalSignatureService {
    @Autowired
    private PDFEditor pdfEditor;
    @Autowired
    private AppUploadPaths appUploadPaths;
    @Autowired
    private VerificationProtocolService verificationProtocolService;
    @Autowired
    private PathResolver pathResolver;

    @Override
    public void setUserStamp(VerificationProtocol protocol, User currentUser) throws OperationNotSupportedException, IOException {
        if (protocol.isSigned()){
            throw new OperationNotSupportedException("Файл уже имеет подпись поверителя. Повторное подписание не возможно");
        }
        if (!protocol.getVerificationEmployee().equals(currentUser)) {
            throw new OperationNotSupportedException("Протокол может подписать только поверитель, выполнивший поверку");
        }
        String sourceFilePath = appUploadPaths.getOriginVerificationProtocolsPath() + "/" + protocol.getStorageFileName();
        pathResolver.createFilePathIfNotExist(appUploadPaths.getSignedVerificationProtocolsPath());
        String signedFilePath = appUploadPaths.getSignedVerificationProtocolsPath() + "/" + protocol.getStorageFileName();
        pdfEditor.addSignatureStamp(sourceFilePath, signedFilePath, protocol.getVerificationEmployee());
        protocol.setSigned(true);
        protocol.setAwaitingSigning(false);
        protocol.setSignedFileName(protocol.getStorageFileName());
        verificationProtocolService.update(protocol);
    }
}
