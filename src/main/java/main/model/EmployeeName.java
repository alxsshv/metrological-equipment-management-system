package main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@XmlRootElement(name = "Name")
@XmlAccessorType(XmlAccessType.NONE)
public class EmployeeName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @XmlElement(name = "Last")
    private String surname;
    @XmlElement(name = "First")
    private String name;
    private String patronymic;

    public void updateFrom(EmployeeName updatingData){
        surname = updatingData.getSurname();
        name = updatingData.getName();
        patronymic = updatingData.getPatronymic();
    }

    @Override
    public String toString() {
        return "EmployeeName{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
