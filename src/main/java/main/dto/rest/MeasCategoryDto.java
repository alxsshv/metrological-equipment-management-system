package main.dto.rest;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MeasCategoryDto {
    private long id;
    private String title;
    private int number;
}
