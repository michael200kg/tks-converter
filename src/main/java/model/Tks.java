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
public class Tks {
    String name;
    List<Person> persons;

    public String toString() {
        return "ТКС: " + this.name + "\n" +
                "СОТРУДНИКИ: \n" +
                this.persons.stream()
                        .map(person -> person.getFio() + " [" + person.getEmail() + "]\n")
                        .collect(Collectors.joining());
    }
}
