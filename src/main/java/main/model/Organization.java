package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "notation")
    private String notation;
    @Column(name = "address")
    private String address;

    public void updateFrom(Organization updateData){
        this.title = updateData.getTitle();
        this.notation = updateData.getNotation();
        this.address = updateData.getAddress();
    }
}
