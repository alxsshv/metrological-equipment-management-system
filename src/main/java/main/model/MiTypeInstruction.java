package main.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "mi_type_instructions")
public class MiTypeInstruction {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "instruction_notation")
    private String instructionNotation; //Обозначение методики поверки
    @Column(name = "instruction_title", length = 2000)
    private String instructionTitle; // Наименование методики поверки
    @Column(name = "humidity_low_limit" )
    private double humidityLowLimit; //Нижнее допустимое значение относительно влажности, %
    @Column(name = "humidity_hi_limit")
    private double humidityHiLimit; //Верхенее допустимое значение относительной влажности, %
    @Column(name = "temperature_low_limit")
    private double temperatureLowLimit; //Нижнее допустимое значение температуры, градусов Цельсия
    @Column(name = "temperature_hi_limit")
    private double temperatureHiLimit; //Верхнее допустимое значение температуры, градусов Цельсия
    @Column(name = "pressure_low_limit")
    private double pressureLowLimit; //Нижнее допустимое значение атмосферного давления, кПа
    @Column(name = "pressure_hi_limit")
    private double pressureHiLimit; //Верхнее допустимое значение атмосферного давления, кПа
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private MiType miType;



    public void updateFrom(MiTypeInstruction updateData){
        this.miType.updateFrom(updateData.getMiType());
        this.pressureHiLimit = updateData.getPressureHiLimit();
        this.pressureLowLimit = updateData.getPressureLowLimit();
        this.temperatureHiLimit = updateData.getTemperatureHiLimit();
        this.temperatureLowLimit = updateData.getTemperatureLowLimit();
        this.humidityHiLimit = updateData.getHumidityHiLimit();
        this.humidityLowLimit = updateData.getHumidityLowLimit();
        this.instructionTitle = updateData.getInstructionTitle();
        this.instructionNotation = updateData.getInstructionNotation();
    }

    @Override
    public String toString() {
        return "MiTypeInstruction{" +
                "id=" + id +
                ", instructionNotation='" + instructionNotation + '\'' +
                ", instructionTitle='" + instructionTitle + '\'' +
                ", humidityLowLimit=" + humidityLowLimit +
                ", humidityHiLimit=" + humidityHiLimit +
                ", temperatureLowLimit=" + temperatureLowLimit +
                ", temperatureHiLimit=" + temperatureHiLimit +
                ", pressureLowLimit=" + pressureLowLimit +
                ", pressureHiLimit=" + pressureHiLimit +
                ", miType=" + miType +
                '}';
    }
}
