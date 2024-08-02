package main.dto.rest;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MiConditionDto {
    private long id;
    private String title;




    @Override
    public String toString() {
        return "MiСondition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
