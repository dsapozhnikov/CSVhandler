import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Calculator {
    //"yyyy-MM-dd hh:mm:ss.SSS"
    // date only "dd-MMM-yyyy"

   public synchronized static String getDateString(long timestamp) {

       Date d = new Date(timestamp*1000);
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
       f.setTimeZone(timeZone);
       System.out.println(f.format(d));
       return f.format(d);
   }

   public synchronized static String getDateForOutPut (long timestamp) {
       Date d = new Date(timestamp*1000);
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
       f.setTimeZone(timeZone);
       System.out.println(f.format(d));
       return f.format(d);
   }

   public synchronized static long getTheBeginningOfTheNextDay (long resultTime) {
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
       f.setTimeZone(timeZone);
       Date d = new Date(resultTime*1000);

       System.out.println("Date "+d);
       Calendar cal = Calendar.getInstance();
       cal.setTimeZone(TimeZone.getTimeZone("UTC"));
       cal.setTime(d);


       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0);
       return cal.getTimeInMillis()/1000;
   }
   public synchronized static boolean compareTimes(long timestamp, long resultTime) {
     String dateBefore = getDateString(timestamp);
     String dateAfter = getDateString(resultTime);
         if (dateBefore.equals(dateAfter))return true;
         return false;

   }
   public static Timestamp getTimeStamp(String date) {

       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
           Date parsedDate = dateFormat.parse(date);
           Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
           return timestamp;
       } catch(Exception e) {
           e.printStackTrace();
       }return null;

   }
}
// comparing dates
//    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//fmt.setTimeZone(...); // your time zone
//        return fmt.format(date1).equals(fmt.format(date2));

// second option , how to convert timestamp to date(not string)

//       Timestamp stamp = new Timestamp(da);
//       Date date = new Date(stamp.getTime());
//       System.out.println(date);