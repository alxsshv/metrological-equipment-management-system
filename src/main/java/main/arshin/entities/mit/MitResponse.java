package main.arshin.entities.mit;

import lombok.*;
import main.arshin.entities.Response;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MitResponse extends Response {
    private MitResult result;
}
