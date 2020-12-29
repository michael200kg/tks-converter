package converter;

import model.Person;
import model.Tks;
import model.TksTemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TksConverter {

    private final static String PATH = "c:/Users/Mikhail_Vershkov/save/";
    private final static String FILENAME = "tks.txt";

    public List<Tks> convert() {
        List<TksTemp> tksTemps = makeTempList(PATH + FILENAME);
        //tksTemps.forEach(tks -> System.out.println(tks.toString()));
        List<Tks> tksList = makeTksList(tksTemps);
        tksList.forEach(tks -> System.out.println(tks.toString()));
        return tksList;
    }

    private List<TksTemp> makeTempList(String filePath) {
        List<TksTemp> tkses = new ArrayList<>();
        List<String> tks = new ArrayList<>();
        try {
            boolean insideTks = false;
            // FileReader fr = new FileReader(filePath);
            // System.out.println("FILE ENCODING: " + fr.getEncoding());
            BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
            // BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            TksTemp currentTks = null;
            while (line != null) {
                if (currentTks != null && endOfTks(line, currentTks.getName())) {
                    currentTks.setEmails(tks);
                    tkses.add(currentTks);
                    insideTks = false;
                }
                if (line.contains("-----")) {
                    line = br.readLine();
                    String tksName = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
                    currentTks = TksTemp.builder().name(tksName).build();
                    tks = new ArrayList<>();
                    insideTks = true;
                    line = br.readLine();
                }

                if (insideTks && !line.trim().isEmpty()) {
                    tks.add(line);
                }
                line = br.readLine();
            }


        } catch (IOException fnfe) {
            System.out.println("Exception while reading file: " + fnfe.getMessage());
        }
        return tkses;
    }

    private List<Tks> makeTksList(List<TksTemp> tksTemps) {
        List<Tks> tksList = new ArrayList<>();
        for (TksTemp tksTemp : tksTemps) {
            StringBuilder emailBuilder = new StringBuilder();
            for (String emailsString : tksTemp.getEmails()) {
                emailBuilder.append(emailsString);
            }
            List<String> emails = new ArrayList<>(Arrays.asList(emailBuilder.toString().split(";")));
            Tks tks = Tks.builder().name(tksTemp.getName()).build();
            tks.setPersons(emails.stream().filter(email -> !email.trim().isEmpty()).map(this::parseEmail).collect(Collectors.toList()));
            tksList.add(tks);
        }
        return tksList;
    }

    private boolean endOfTks(String line, String tksName) {
        return line.contains("</" + tksName + ">");
    }

    private Person parseEmail(String emailString) {
        Person person = new Person();
        if (emailString.contains("<") && emailString.contains(">")) {
            person.setEmail(emailString.substring(emailString.indexOf("<") + 1, emailString.indexOf(">")));
            if (emailString.trim().startsWith("<")) {
                person.setFio(emailString.trim().substring(1, emailString.trim().indexOf("@")).trim());
            } else {
                person.setFio(emailString.trim().substring(0, emailString.trim().indexOf("<")).trim());
            }
        } else {
            person.setFio(emailString.trim().substring(0, emailString.trim().indexOf("@")).trim());
            person.setEmail(emailString.trim());
        }
        return person;
    }

}
