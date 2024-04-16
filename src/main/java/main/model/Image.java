package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "storage_file_name")
    private String storageFileName;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "extension")
    private String extension;
    @Column(name = "description")
    private String description;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "category_id")
    private long categoryId;
    @CreationTimestamp
    @Column(name = "uploading_date")
    private LocalDateTime uploadingDate;
}
