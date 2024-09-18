package main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "system_roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Наименование роли не может быть пустым")
    private String name;
    @NotEmpty(message = "Псевдоним роли не может быть пустым")
    private String pseudonym;
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
