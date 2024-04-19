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
    @OneToMany(mappedBy = "miType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "modifications")
    @JsonIgnore
    private List<MiTypeModification> modifications = new ArrayList<>(); // Модификации



    public void addModification(MiTypeModification modification){
        modification.setMiType(this);
        modifications.add(modification);
    }

    public void setModifications(List<MiTypeModification> modifications){
        modifications.forEach(modification -> modification.setMiType(this));
        this.modifications = modifications;
    }



    public void removeModification(MiTypeModification modification){
        modification.setMiType(null);
        modifications.remove(modification);
    }

    public void updateFrom(MiType newData){
        this.number = newData.getNumber();
        this.title = newData.getTitle();
        this.notation = newData.getNotation();
        this.startDate = newData.getStartDate();
        this.endDate = newData.getEndDate();
        this.verificationPeriod = newData.getVerificationPeriod();
        this.modifications.clear();
        this.modifications.addAll(newData.getModifications());
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
                ", modifications=" + modifications +
                '}';
    }
}
