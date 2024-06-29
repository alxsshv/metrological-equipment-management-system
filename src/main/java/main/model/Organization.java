package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "notation")
    private String notation;
    @Column(name = "address")
    private String address;
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @Column(name = "updating_date_time")
    private LocalDateTime updatingDateTime;

    public void updateFrom(Organization updateData){
        this.title = updateData.getTitle();
        this.notation = updateData.getNotation();
        this.address = updateData.getAddress();
        this.updatingDateTime = LocalDateTime.now();
    }
}
