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
@Table(name = "mi_condition")
public class MiCondition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "title")
    private String title; // обозначение состояния СИ




    public void updateFrom(MiCondition updateData){
        this.title = updateData.getTitle();
    }

    @Override
    public String toString() {
        return "MiСondition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
