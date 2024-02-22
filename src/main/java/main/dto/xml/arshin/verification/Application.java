package main.dto.xml.arshin.verification;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@NoArgsConstructor
@XmlRootElement


public class Application {

    @XmlElement(name = "result")
    private ArrayList<Result> results = new ArrayList<>();

    public boolean add (Result result){
        return results.add(result);
    }

}
