package com.gmail.koivisto.p.mika.soccertimer;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GameTime {
    public Date getCurrentTimeAndDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
    public String getCurrentTimeAndDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        d = getCurrentTimeAndDate();
        String formattedDate = df.format(getCurrentTimeAndDate());
        return formattedDate;
    }
    public String getCurrentTimeStr() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d;
        d = getCurrentTimeAndDate();
        String formattedDate = df.format(getCurrentTimeAndDate());
        return formattedDate;
    }
    public Time getTimeDiff(Calendar cal1, Calendar cal2) {
        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal2.getTimeInMillis();
        long diff = milis2 - milis1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        String tmp = diffMinutes + " dk. " + (diffSeconds - diffMinutes * 60)
                + " sn " + (diff - (diffSeconds * 1000)) + " ms.";
        Time t = new Time(diff);
        return t;
    }
    public static Date setCalenderTime(Calendar cal,int hour, int min, int sec, int milSec) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.MILLISECOND, milSec);
        return cal.getTime();
    }

    public Time timeSum(Time diffTime, Time diffTime2) {
        Time sum = new Time(diffTime.getTime() + diffTime2.getTime());
        return sum;
    }
    public Calendar timeSum(Calendar diffTime, Calendar diffTime2,Calendar sum) {

        int hours = diffTime.get(Calendar.HOUR_OF_DAY);
        int min = diffTime.get(Calendar.MINUTE);
        int sec = diffTime.get(Calendar.SECOND);
        int millSec = diffTime.get(Calendar.MILLISECOND);
        int hours2 = diffTime2.get(Calendar.HOUR_OF_DAY);
        int min2 = diffTime2.get(Calendar.MINUTE);
        int sec2 = diffTime2.get(Calendar.SECOND);
        int millSec2 = diffTime2.get(Calendar.MILLISECOND);
        sum.add(Calendar.YEAR, diffTime2.get(Calendar.YEAR));
        sum.add(Calendar.MONTH, diffTime2.get(Calendar.MONTH) + 1); // Months are zero-based!
        sum.add(Calendar.DATE, diffTime2.get(Calendar.DATE));
        sum.add(Calendar.HOUR_OF_DAY, diffTime2.get(Calendar.HOUR_OF_DAY));
        sum.add(Calendar.MINUTE, diffTime2.get(Calendar.MINUTE));
        sum.add(Calendar.SECOND, diffTime2.get(Calendar.SECOND));
        sum.add(Calendar.MILLISECOND, diffTime2.get(Calendar.MILLISECOND));

        /*
        int hours = diffTime.get(Calendar.HOUR_OF_DAY)+diffTime2.get(Calendar.HOUR_OF_DAY);
        int min = diffTime.get(Calendar.MINUTE)+diffTime2.get(Calendar.MINUTE);
        int sec = diffTime.get(Calendar.SECOND)+diffTime2.get(Calendar.SECOND);
        int millSec = diffTime.get(Calendar.MILLISECOND)+diffTime2.get(Calendar.MILLISECOND);

        setCalenderTime(sum,hours,min,sec,millSec);
*/
        return sum;
    }

    public void getTimeDiff(Calendar cal1, Calendar cal12, Calendar diffTime2_1) {
        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal12.getTimeInMillis();
        int diff = (int) (milis2 - milis1);
        int diffHours = diff / (60*60 * 1000);
        int diffHoursInMs = diffHours*60*60 * 1000;
        int diffMinutes = ((diff-(diffHoursInMs)) / (60 * 1000));
        int diffMinutesInMs = diffMinutes * 60* 1000;
        int diffSeconds = ((diff-diffHoursInMs-diffMinutesInMs) / 1000);
        int diffSecondsInMs = diffSeconds * 1000;
        int millSeconds = diff-diffHoursInMs-diffMinutesInMs-diffSecondsInMs;
        String tmp = diffMinutes + " dk. " + (diffSeconds - diffMinutes * 60)
                + " sn " + (diff - (diffSeconds * 1000)) + " ms.";
        setCalenderTime(diffTime2_1,diffHours,diffMinutes,diffSeconds,millSeconds);
    }
}
