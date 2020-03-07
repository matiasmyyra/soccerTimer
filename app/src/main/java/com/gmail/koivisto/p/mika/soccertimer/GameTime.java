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
}
