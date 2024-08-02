package main.arshin;

import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VerificationRequestBuilder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Value("${arshin.verification.uri}")
    private String uriBase;
    private String orgTitle;
    private String mitNumber;
    private String mitTitle;
    private String mitNotation;
    private String miModification;
    private String miNumber;
    private String verificationDate;
    private String validDate;


    public VerificationRequestBuilder() {
    }

    public VerificationRequestBuilder uri(String uri) {
        this.uriBase = uri;
        return this;
    }

    public VerificationRequestBuilder orgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
        return this;
    }

    public VerificationRequestBuilder mitNumber(String mitNumber) {
        this.mitNumber = mitNumber;
        return this;
    }

    public VerificationRequestBuilder mitTitle(String mitTitle) {
        this.mitTitle = mitTitle;
        return this;
    }

    public VerificationRequestBuilder mitNotation(String mitNotation) {
        this.mitNotation = mitNotation;
        return this;
    }

    public VerificationRequestBuilder miModification(String miModification) {
        this.miModification = miModification;
        return this;
    }

    public VerificationRequestBuilder miNumber(String miNumber) {
        this.miNumber = miNumber;
        return this;
    }

    public VerificationRequestBuilder verificationDate(LocalDate verificationDate) {
        this.verificationDate = verificationDate.format(formatter);
        return this;
    }

    public VerificationRequestBuilder validDate(LocalDate validDate) {
        this.validDate = validDate.format(formatter);
        return this;
    }

    public String build(){
        List<String> parameters = new ArrayList<>();
        if (orgTitle != null) {
            parameters.add("org_title=" + orgTitle);
        }
        if (mitNumber != null) {
            parameters.add("mit_number=" + mitNumber);
        }
        if (mitTitle != null) {
            parameters.add("mit_title=" + mitTitle);
        }
        if (mitNotation != null) {
            parameters.add("mit_notation=" + mitNotation);
        }
        if (miModification != null) {
            parameters.add("mi_modification=" + miModification);
        }
        if (miNumber != null) {
            parameters.add("mi_number=" + miNumber);
        }
        if (verificationDate != null) {
            parameters.add("verification_date=" + verificationDate);
        }
        if (validDate != null){
            parameters.add("valid_date=" + validDate);
        }
        List<String> parametersWithDelimiter = insertDelimiter(parameters,"&");
        String parametersString = String.join("", parametersWithDelimiter);
        return uriBase + parametersString;
    }

    private List<String> insertDelimiter(List<String> list, String delimiter) {
        return list.stream().flatMap(s -> Stream.of(s, delimiter))
                .limit(list.size() * 2L - 1).toList();
    }



}
