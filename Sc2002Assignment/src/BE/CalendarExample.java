package BE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provides functionalities related to handling dates using the Calendar class.
 */
public class CalendarExample {
    // Date formatter for consistent date string parsing and formatting
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Retrieves the current date as a formatted string.
     *
     * @return A string representation of the current date in "dd/MM/yyyy" format.
     */
    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = cal.getTime();
        return dateFormatter.format(currentDate);
    }

    /**
     * Checks if the given date string is equal to or after the current date.
     *
     * @param dateString The date string to be compared in "dd/MM/yyyy" format.
     * @return True if the given date is equal to or after the current date, false otherwise.
     */
    public static boolean isDateEqualOrAfterCurrentDate(String dateString) {
        try {
            Date currentDate = dateFormatter.parse(getCurrentDate());
            Date registrationDate = dateFormatter.parse(dateString);
            return !registrationDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
