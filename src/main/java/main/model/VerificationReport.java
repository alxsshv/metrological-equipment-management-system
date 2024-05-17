package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "verification_report")
public class VerificationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreationTimestamp
    @Column(name = "creation_time")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateDate;
    @Column(name = "comment")
    private String comment;
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationRecord> records = new ArrayList<>();

    public void addRecord(VerificationRecord record){
        records.add(record);
        record.setReport(this);
    }

    public void removeRecord(VerificationRecord record){
        records.remove(record);
        record.setReport(null);
    }
}
