package main.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@Table(name = "employees")
@XmlRootElement(name = "ApprovedEmployee")
@XmlAccessorType(XmlAccessType.NONE)
public class Employee {
    @Id
    private long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    @Column(name = "snils")
    private String snils;

    public void updateFrom(Employee updatingData){
        snils = updatingData.getSnils();
    }


}

