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
@Table(name = "mi_types")
@NoArgsConstructor
@AllArgsConstructor
public class MiType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "number")
    private String number; //Номер в реестре утвержденного типа СИ
    @Column(name = "title")
    private String title; //Наименование типа СИ
    @Column(name = "notation")
    private String notation; //Обозначение типа СИ
    @Column(name = "start_date")
    private LocalDate startDate; //Начало срока действия
    @Column(name = "end_date")
    private LocalDate endDate; //Окончание срока действия
    @Column(name = "verification_period")
    private double verificationPeriod; //межповерочный интервал, лет
    @Column(name = "mi_title_template")
    private String miTitleTemplate; //Шаблон для наименования средства измерений, на основе данного типа СИ





    public void updateFrom(MiType newData){
        this.number = newData.getNumber();
        this.title = newData.getTitle();
        this.notation = newData.getNotation();
        this.startDate = newData.getStartDate();
        this.endDate = newData.getEndDate();
        this.verificationPeriod = newData.getVerificationPeriod();
        this.miTitleTemplate = newData.getMiTitleTemplate();

    }

    @Override
    public String toString() {
        return "MiType{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", notation='" + notation + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", verificationPeriod=" + verificationPeriod +
                '}';
    }
}
