package main.arshin.entities.vri;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import main.arshin.entities.Result;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonDeserialize
public class VriResult extends Result {
    private int count;
    private int start;
    private int rows;
    private List<VriItem> items = new ArrayList<>();
}
