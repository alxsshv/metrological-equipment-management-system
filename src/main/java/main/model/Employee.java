package main.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement(name = "ApprovedEmployee")
@XmlAccessorType(XmlAccessType.NONE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String snils;

    public void updateFrom(Employee updatingData){
        surname = updatingData.getSurname();
        name = updatingData.getName();
        patronymic = updatingData.getPatronymic();
        snils = updatingData.getSnils();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", snils='" + snils + '\'' +
                '}';
    }
}

