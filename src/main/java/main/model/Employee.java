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
    private int id;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @XmlElement(name = "Name")
    private EmployeeName employeeName;
    @XmlElement(name = "SNILS")
    private String snils;

    public void updateFrom(Employee updatingData){
        employeeName.updateFrom(updatingData.getEmployeeName());
        snils = updatingData.getSnils();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeName=" + employeeName +
                ", snils=" + snils +
                '}';
    }
}

