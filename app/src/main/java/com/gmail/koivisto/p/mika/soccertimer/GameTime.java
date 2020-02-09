package com.gmail.koivisto.p.mika.soccertimer;
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

}
