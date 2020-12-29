import converter.TksConverter;
import model.Tks;
import saver.OracleSaver;

import java.util.List;

public class TksConverterMain {
    public static void main(String[] args) {
        List<Tks> tksList = new TksConverter().convert();
        tksList.forEach(tks -> System.out.println(tks.toString()));
        new OracleSaver().save(tksList);
    }
}
