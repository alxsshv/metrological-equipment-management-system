package main.dto.xml.fsa.factory.converter;

public class ApplicableToResultVerificationConverter {
    public static int convert(Boolean applicable){
        if (applicable){
            return 1;
        }
        return 2;
    }
}
