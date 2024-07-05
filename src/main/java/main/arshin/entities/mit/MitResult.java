package main.arshin.entities.mit;

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
public class MitResult extends Result {
    private int count;
    private int start;
    private int rows;
    private List<MitItem> items = new ArrayList<>();
}
