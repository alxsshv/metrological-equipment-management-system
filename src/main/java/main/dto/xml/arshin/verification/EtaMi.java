package main.dto.xml.arshin.verification;



import jakarta.xml.bind.annotation.XmlRootElement;


//TODO Разобраться с ЛПС и ГПС по РП внешний публичный интерфейс стр 49

@XmlRootElement
public class EtaMi {
    private String regNumber; // регистрационный номер СИ в перечне эталонов
    private String miTypeNumber;
    private String miTypeURL;
    private String miTypeTitle;
    private String miTypeType;
    private String modification;
    private String manufactureNum;
    private int manufactureYear;
    private String rankCode;
    private String rankTitle;
    private String schemaTitle;



}
