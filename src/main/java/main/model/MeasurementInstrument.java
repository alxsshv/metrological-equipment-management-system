package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "measurement_instruments")
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementInstrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    private int id;
    @Column(name = "mi_type_id")
    private int miTypeId; // Идентификатор типа СИ
    @Column(name = "modification")
    private String modification; // Модификация
    @Column(name = "serial_number")
    private String serialNum; //Заводской номер или серийный номер
    @Column(name = "inventory_number")
    private String inventoryNum; //Инвентарный номер или буквенное-цифровое обозначение СИ
    @Column(name = "manufacture_year")
    private int manufactureYear; // Год выпуска
    @Column(name = "commissioning_year")
    private int commissioningYear; //Год ввода в эксплуатацию
    @Column(name = "verification_date")
    private LocalDate verificationDate; //Дата поверки
    @Column(name = "valid_date")
    private LocalDate validDate; //Дата действия поверки
    @Column(name = "applicable")
    private boolean applicable; //Результат поверки
    @Column(name = "owner")
    private String owner; // Владелец СИ

    public void updateFrom(MeasurementInstrument updatingData){
        this.miTypeId = updatingData.miTypeId;
        this.modification = updatingData.getModification();
        this.serialNum = updatingData.getSerialNum();
        this.inventoryNum = updatingData.getInventoryNum();
        this.verificationDate = updatingData.getVerificationDate();
        this.validDate = updatingData.getValidDate();
        this.applicable = updatingData.isApplicable();
        this.manufactureYear = updatingData.getManufactureYear();
        this.commissioningYear = updatingData.getCommissioningYear();
        this.owner = updatingData.getOwner();
    }
}
