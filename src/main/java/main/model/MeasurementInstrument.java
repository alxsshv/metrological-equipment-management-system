package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "measurement_instruments")
public class MeasurementInstrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mi_type_id")
    private MiType miType; // Идентификатор типа СИ
    @Column(name ="title")
    private String title;
    @Column(name = "modification")
    private String modification; // Модификация
    @Column(name = "serial_number")
    private String serialNum; // Заводской номер или серийный номер
    @Column(name = "inventory_number")
    private String inventoryNum; // Инвентарный номер или буквенное-цифровое обозначение СИ
    @Column(name = "manufacture_date")
    private LocalDate manufactureDate; // Дата выпуска
    @Column(name = "start_use_date")
    private LocalDate startUseDate; //Дата ввода в эксплуатацию
    @Column(name = "verification_date")
    private LocalDate verificationDate; //Дата поверки
    @Column(name = "valid_date")
    private LocalDate validDate; //Дата действия поверки
    @Column(name = "applicable")
    private boolean applicable; //Результат поверки
    @Column(name = "verification_organization")
    private String verificationOrganization; // Организация-поверитель
    @Column(name = "certificate_number")
    private String certificateNumber; // номер свидетельства о поверке
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Organization owner; // Владелец СИ
    @Column(name = "mi_user")
    private String user; // Ответственный за эксплуатацию
    @CreationTimestamp
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @UpdateTimestamp
    @Column(name = "updating_date_time")
    private LocalDateTime updatingDateTime;

    public void updateFrom(MeasurementInstrument updatingData){
        this.miType = updatingData.getMiType();
        this.title = updatingData.getMiType().getMiTitleTemplate();
        this.modification = updatingData.getModification();
        this.serialNum = updatingData.getSerialNum();
        this.inventoryNum = updatingData.getInventoryNum();
        this.verificationDate = updatingData.getVerificationDate();
        this.validDate = updatingData.getValidDate();
        this.applicable = updatingData.isApplicable();
        this.manufactureDate = updatingData.getManufactureDate();
        this.startUseDate = updatingData.getStartUseDate();
        this.owner = updatingData.getOwner();
        this.user = updatingData.getUser();
    }
}
