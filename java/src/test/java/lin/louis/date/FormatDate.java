package lin.louis.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class FormatDate {

    @Test
    void formatDate() {
        GregorianCalendar calendar = new GregorianCalendar(2014, 2, 17);
        Date date = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);

        assertThat(formattedDate).isEqualTo("2014-03-17");
    }
}
