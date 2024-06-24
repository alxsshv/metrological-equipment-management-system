package main.dto.xml.fsa.factory.converter;

public class ArshinVerificationNumToNumberVerificationConverter {
    public static String convert(String arshinVerificationNumber){
        if (arshinVerificationNumber == null || arshinVerificationNumber.isEmpty()){
            throw new NullPointerException("Номер записи о поверке в ФГИС Аршин не может быть пустым");
        }
        String[] numberParts = arshinVerificationNumber.split("/");
        if (numberParts.length == 3){
            return numberParts[2];
        } else {
            throw new RuntimeException("Ошибка преобразования номера записи о поверке");
        }
    }
}
