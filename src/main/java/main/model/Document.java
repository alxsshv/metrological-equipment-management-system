package main.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "upload_name")
    private String uploadName;
    @Column(name = "storage_file_name")
    private String storageFileName;
    @Column(name = "description")
    private String description;
    @Column(name = "extension")
    private String extension;
    @ManyToOne(fetch = FetchType.LAZY)
    private MiTypeInstruction miTypeInstruction;
    @Timestamp
    @Column(name = "uploading_date")
    private LocalDateTime uploadingDate;
}
