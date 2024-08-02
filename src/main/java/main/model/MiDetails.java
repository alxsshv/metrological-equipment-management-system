package main.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mi_details")
public class MiDetails {
    @Id
    private long id;
    @Column(name = "inventory_number")
    private String inventoryNum; // Инвентарный номер
    @Column(name = "mi_registry_number")
    private String miRegistryNumber; // Номер в перечне средств измерений, применяемых в организации
    @Column(name = "manufacture_date")
    private LocalDate manufactureDate; // Дата выпуска
    @Column(name = "start_use_date")
    private LocalDate startUseDate; //Дата ввода в эксплуатацию
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mi_condition_id")
    private MiCondition condition; // Состояние СИ - в работе, на хранении, в ремонте и пр.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mi_status_id")
    private MiStatus status; //эталон, рабочее си, индикатор
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department; // Эксплуатирующее подразделение
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measurement_category_id")
    private MeasCategory measCategory; // Вид измерений
    @OneToMany(mappedBy = "miDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "characteristics")
    @JsonIgnore
    private List<MiCharacteristic> characteristics = new ArrayList<>(); // Модификации
    @Column(name = "mi_user")
    private String user; // Ответственный за эксплуатацию
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private MeasurementInstrument mi;



    public void updateFrom(MiDetails updateData){
        this.inventoryNum = updateData.getInventoryNum();
        this.miRegistryNumber = updateData.getMiRegistryNumber();
        this.manufactureDate = updateData.getManufactureDate();
        this.startUseDate = updateData.getStartUseDate();
        this.condition = updateData.getCondition();
        this.status = updateData.getStatus();
        this.department = updateData.getDepartment();
        this.measCategory = updateData.getMeasCategory();
        this.user = updateData.getUser();
    }

    @Override
    public String toString() {
        return "MiDetails{" +
                ", inventoryNum='" + inventoryNum + '\'' +
                ", miRegistryNumber='" + miRegistryNumber + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", startUseDate=" + startUseDate +
                ", condition=" + condition +
                ", status=" + status +
                ", department=" + department +
                ", measCategory=" + measCategory +
                ", characteristics=" + characteristics +
                ", user='" + user + '\'' +
                ", mi=" + mi +
                '}';
    }
}
