package main.service.utils.fileInfos;


import lombok.Getter;
import lombok.Setter;
import main.service.Category;

@Getter
@Setter
public class ProtocolFileInfo extends FileInfo {
    private String number;
    private String verificationDate;
    private long journalId;
    private long verificationEmployeeId;

    public ProtocolFileInfo() {
    }

    public ProtocolFileInfo(String description, Category category, long categoryId) {
        super(description, category, categoryId);
    }


    public ProtocolFileInfo(String description,
                            Category category,
                            long categoryId,
                            String number,
                            String verificationDate,
                            long journalId, long verificationEmployeeId) {
        super(description, category, categoryId);
        this.number = number;
        this.verificationDate = verificationDate;
        this.journalId = journalId;
        this.verificationEmployeeId = verificationEmployeeId;
    }

    @Override
    public String toString() {
        return "ProtocolFileInfo{" +
                "number='" + number + '\'' +
                ", verificationDate='" + verificationDate + '\'' +
                ", journalId=" + journalId +
                ", verificationEmployeeId=" + verificationEmployeeId +
                '}';
    }
}
