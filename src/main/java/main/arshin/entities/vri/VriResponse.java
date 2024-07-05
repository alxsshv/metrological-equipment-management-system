package main.arshin.entities.vri;

import lombok.*;
import main.arshin.entities.Response;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VriResponse extends Response {
    private VriResult result;
}
