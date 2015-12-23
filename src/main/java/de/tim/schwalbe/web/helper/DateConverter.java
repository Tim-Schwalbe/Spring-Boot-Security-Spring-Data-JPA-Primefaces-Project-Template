package de.tim.schwalbe.web.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tschwalbe on 09.09.2015.
 */
public class DateConverter {

        public static String prettyTimeStamp(Date date) {
                return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(date);
        }
}
