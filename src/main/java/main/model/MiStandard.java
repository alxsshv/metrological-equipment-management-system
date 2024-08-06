package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mi_standards")
public class MiStandard {
    // Средства измерений, применяемые в качестве эталона
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "arshin_number")
    private String arshinNumber; // номер эталона, присвоенный организацией-владельцем
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mi_id")
    private MiDetails miDetails; // Средство измерения, являющееся эталоном
    @Column(name = "schema_type")
    private String schemaType; // локальная или государственная поверочная схема
    @Column(name = "schema_title", length = 2000)
    private String schemaTitle; //полное наименование поверочной схемы
    @Column(name= "schema_notation")
    private String schemaNotation; // краткое наименование поверочной схемы
    @Column(name = "state_standard_num")
    private String stateStandardNumber; // номер государственного поверочного
    @Column(name = "level")
    private String level; // код рязряда по поверочой схеме
    @CreationTimestamp
    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
    @UpdateTimestamp
    @Column(name = "updating_date_time")
    private LocalDateTime updatingDateTime;


    public void updateFrom(MiStandard updateData){
        this.arshinNumber = updateData.getArshinNumber();
        this.schemaType = updateData.getSchemaType();
        this.schemaTitle = updateData.getSchemaTitle();
        this.level = updateData.getLevel();
        this.schemaNotation = updateData.getSchemaNotation();
        this.stateStandardNumber = updateData.getStateStandardNumber();
        this.updatingDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "MiStandard{" +
                "id=" + id +
                ", arshinNumber='" + arshinNumber + '\'' +
                ", schemaType='" + schemaType + '\'' +
                ", schemaTitle='" + schemaTitle + '\'' +
                ", schemaNotation='" + schemaNotation + '\'' +
                ", stateStandardNumber='" + stateStandardNumber + '\'' +
                ", level='" + level + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", updatingDateTime=" + updatingDateTime +
                '}';
    }
}
