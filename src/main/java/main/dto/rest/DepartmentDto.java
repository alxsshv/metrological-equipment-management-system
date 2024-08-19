package main.dto.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
    private long id;
    @NotBlank(message = "Пожалуйста заполните обозначение подразделения")
    private String notation;

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id=" + id +
                ", notation='" + notation + '\'' +
                '}';
    }
}
