package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "settings")
public class Settings {
    @Id
    private int id = 1;
    @Column(name = "organization_notation")
    private String organizationNotation;
    @Column(name = "organization_title")
    private String organizationTitle;
    @Column(name = "sign_cipher")
    private String signCipher;

    public void updateFrom(Settings settings){
        this.organizationNotation = settings.getOrganizationNotation();
        this.organizationTitle = settings.getOrganizationTitle();
    }
}
