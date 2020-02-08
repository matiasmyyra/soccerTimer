package com.gmail.koivisto.p.mika.soccertimer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameTime {
    public String getCurrentTimeAndDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}
