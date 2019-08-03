import org.junit.Test;

//import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


public class tt {

    @Test
    public void method() {
//        java.util.Date date = new java.util.Date();
//        Date dateq = new Date(date.getTime()+ 3306 * 24 * 5);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println(timestamp);

//        date.setTime(date.getTime()+ 3306 * 24 * 5);
//        System.out.println(dateq.getTime());
    }
}
