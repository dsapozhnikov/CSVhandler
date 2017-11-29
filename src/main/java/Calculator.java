
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/*

Создаем класс Калькулятор для калькуляции всех необходимых значений используемых в классе ClientHandler
методы решил сделать синзронными... на всякий случай :) б насколько я понимаю при использовании ExecuterService каждому потоку
отдается отдельная задача , в нашем случае отдельный файл из input  папки  следовательно по лрогике вещей не должно возникнуть ситуации при которой
более одного потока итают из одного итого же файла... было бы неплохо получить какие нибудь коментарии по даннуму вопросу если будет возможность
 */
public class Calculator {


    //"yyyy-MM-dd hh:mm:ss.SSS"
    // date only "dd-MMM-yyyy"


    // получаем дату в форме строки для сравнения двух дат ( была ли сессия начата в один день и закончена в другой )

   public synchronized static String getDateString(long timestamp) {

       Date d = new Date(timestamp*1000);
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
       f.setTimeZone(timeZone);

       return f.format(d);
   }
   //почти такой же метод только с другим форматом даты -специально для вывода в результирующий файл


   public synchronized static String getDateForOutPut (long timestamp) {
       Date d = new Date(timestamp*1000);
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
       f.setTimeZone(timeZone);

       return f.format(d);
   }

 //  находим начало следующего дня для ситуации если одна транзакция началась в один и закончилась в другой день

   public synchronized static long getTheBeginningOfTheNextDay (long resultTime) {
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
       DateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
       f.setTimeZone(timeZone);
       Date d = new Date(resultTime*1000);


       Calendar cal = Calendar.getInstance();         // используем Calendar  для  выставления часов/минут/секунд/ данного дня в 0;
       cal.setTimeZone(TimeZone.getTimeZone("UTC"));
       cal.setTime(d);

       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0);
       return cal.getTimeInMillis()/1000;        // получяем 00:00 данного дня в миллисекундах- необходимое для подсчета разницы во времению

   }
   // сравневаем даты (логика проверки не штк, но из-за нехватки времени пришлось оставить эту реализацию
   public synchronized static boolean compareTimes(long timestamp, long resultTime) {
     String dateBefore = getDateString(timestamp);
     String dateAfter = getDateString(resultTime);
         if (dateBefore.equals(dateAfter))return true;
         return false;

   }

}
