package main.service.interfaces;

import main.model.User;
import main.model.VerificationProtocol;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;


public interface DigitalSignatureService {
    void setUserStamp(VerificationProtocol verificationProtocol, User currentUser) throws OperationNotSupportedException, IOException;
}
