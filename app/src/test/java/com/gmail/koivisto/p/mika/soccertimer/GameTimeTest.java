package com.gmail.koivisto.p.mika.soccertimer;
import org.junit.Test;

import java.sql.Time;
import java.util.Calendar;

import static org.junit.Assert.*;

public class GameTimeTest {
    @Test
    public void getTimeDiffTest() {
        GameTime sut = new GameTime();
        Calendar cal1 = Calendar.getInstance();
        sut.setCalenderTime(cal1,0,0,0,0);
        Calendar cal12 = Calendar.getInstance();
        sut.setCalenderTime(cal12,0,3,2,100);
        Time diffTime = sut.getTimeDiff(cal1,cal12);
        int expectetDiffMs = 3*60*1000+2*1000+100;
        assertEquals(expectetDiffMs,diffTime.getTime());

        sut.setCalenderTime(cal1,0,3,2,100);

        sut.setCalenderTime(cal12,0,3,2,100+1000);

        Time diffTime2 = sut.getTimeDiff(cal1,cal12);
        Time sum = sut.timeSum(diffTime,diffTime2);
        assertEquals((expectetDiffMs+1000),sum.getTime());


    }



}
