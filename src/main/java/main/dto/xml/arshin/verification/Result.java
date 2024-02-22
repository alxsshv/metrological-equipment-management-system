package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name="result")
public class Result {
    @XmlElement
    private MiInfo miInfo;
    @XmlElement
    private String signCipher; // условный шифр знака поверки
    @XmlElement
    private String miOwner; // владелец СИ
    @XmlElement
    private String vrfDate; // дата поверки
    @XmlElement
    private String validDate; // действительно до
    @XmlElement
    private int type; // признак первичной или периодической поверки
    @XmlElement
    private boolean calibration; // поверка  с использованием результатов калибровки
    @XmlElement
    private Applicable applicable;
    @XmlElement
    private Inapplicable inapplicable;
    @XmlElement
    private String docTitle; //Наименование документа на основании которого выполнена поверка
    @XmlElement
    private String metrologist; // ФИО поверителя
    @XmlElement
    private Means means;
    @XmlElement
    private Conditions conditions;
    @XmlElement
    private String structure;
    @XmlElement
    private BriefProcedure briefProcedure;

//    private Protocol protocol;






}
