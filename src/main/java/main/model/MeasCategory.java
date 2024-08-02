package main.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurement_category")
public class MeasCategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title; //Обозначение вида измерений
    @Column(name = "number")
    private int number; //Номер вида измерений



    public void updateFrom(MeasCategory updateData){
        this.title = updateData.getTitle();
        this.number = updateData.getNumber();
    }

    @Override
    public String toString() {
        return "MeasCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", number=" + number +
                '}';
    }
}
