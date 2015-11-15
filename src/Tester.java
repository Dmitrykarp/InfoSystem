import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Support on 15.11.2015.
 */
public class Tester {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("dd.mm.yyyy");

        Date date = new Date();
        date = format1.parse("29.12.1990");
        Date current = new Date();
        System.out.println(date-current);
    }
}
