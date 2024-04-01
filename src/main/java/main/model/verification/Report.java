package main.model.verification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @CreationTimestamp
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> records = new ArrayList<>();
}
