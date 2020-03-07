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
        Calendar diffTime_1 = Calendar.getInstance();
        sut.getTimeDiff(cal1,cal12,diffTime_1);
        int expectetDiffMs = 3*60*1000+2*1000+100;
        assertEquals(expectetDiffMs,diffTime.getTime());

        sut.setCalenderTime(cal1,0,3,2,100);

        sut.setCalenderTime(cal12,0,3,2,100+1000);

        Time diffTime2 = sut.getTimeDiff(cal1,cal12);
        Calendar diffTime2_1 = Calendar.getInstance();
        sut.getTimeDiff(cal1,cal12,diffTime2_1);
        Time sum = sut.timeSum(diffTime,diffTime2);
        assertEquals((expectetDiffMs+1000),sum.getTime());

        Calendar sum2 = Calendar.getInstance();
        sut.timeSum(diffTime_1,diffTime2_1,sum2);
        assertEquals(0,sum2.get(Calendar.HOUR_OF_DAY));
        assertEquals(3,sum2.get(Calendar.MINUTE));
        assertEquals(2,sum2.get(Calendar.SECOND));
        assertEquals(100+1000,sum2.get(Calendar.MILLISECOND));


    }



}
