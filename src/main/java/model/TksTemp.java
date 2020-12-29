package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TksTemp {
    String name;
    List<String> emails;

    public String toString() {
        return "ТКС: " + this.name + "\n" +
                "EMAILS: \n" +
                String.join("\n", emails);
    }
}
