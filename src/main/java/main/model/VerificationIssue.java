package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @CreationTimestamp
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationRecord> verificationRecords = new ArrayList<>();

    @Override
    public String toString() {
        return "VerificationIssue{" +
                "id=" + id +
                ", creationDateTime=" + creationDateTime +
                ", verificationRecords=" + verificationRecords +
                '}';
    }
}
