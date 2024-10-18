package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Verification_protocols")
public class VerificationProtocol{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "number")
    private String number;
    @Column(name = "storage_file_name")
    private String storageFileName;
    @Column(name = "original_file_name")
    private String originalFilename;
    @Column(name = "signed_file_name")
    private String signedFileName;
    @Column(name = "description")
    private String description;
    @Column(name = "extension")
    private String extension;
    @Column(name = "verification_date")
    private LocalDate verificationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private VerificationJournal journal;
    @ManyToOne(fetch = FetchType.EAGER)
    private MeasurementInstrument instrument;
    @Column(name = "awaitingSigning")
    private boolean awaitingSigning;
    @Column(name = "signed")
    private boolean signed;
    @CreationTimestamp
    @Column(name = "uploading_date")
    private LocalDateTime uploadingDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updatingDate;
}
