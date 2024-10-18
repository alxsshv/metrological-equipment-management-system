package main.dto.rest;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationProtocolDto extends DocumentDto{

    private long id;
    private String number;
    private String storageFileName;
    private String originalFilename;
    private String signedFileName;
    private String description;
    private String extension;
    private long journalId;
    private MiFullDto instrumentDto;
    private boolean awaitingSigning;
    private boolean signed;
    private String verificationDate;
    private String uploadingDate;
    private String updatingDate;
}
