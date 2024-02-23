package main.model.verification;

import main.model.Employee;

import java.time.LocalDate;

public class VerificationConditions {
    private double temperature;
    private double pressure;
    private double humidity;

    public static class Record {
        private int id;
        private String miTypeNumber;
        private String serialNumber;
        private String modification;
        private String code;
        private String miOwner;
        private LocalDate verificationDate;
        private LocalDate validDate;
        private int verificationType;
        private boolean result;
        private String stickerNumber;
        private String procedureDocumentNotation;
        private Employee employee;
        private String etalon;
        private VerificationConditions conditions;
        private String note;
    }
}
