package main.dto.rest;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MiConditionDto {
    private long id;
    @NotEmpty(message = "Пожалуйста заполните наименование состояния СИ")
    private String title;




    @Override
    public String toString() {
        return "MiСondition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
