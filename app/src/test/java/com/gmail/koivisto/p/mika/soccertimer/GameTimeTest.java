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
        assertEquals(0,diffTime_1.get(Calendar.HOUR_OF_DAY));
        assertEquals(3,diffTime_1.get(Calendar.MINUTE));
        assertEquals(2,diffTime_1.get(Calendar.SECOND));
        assertEquals(100,diffTime_1.get(Calendar.MILLISECOND));
        int expectetDiffMs = 3*60*1000+2*1000+100;
        assertEquals(expectetDiffMs,diffTime.getTime());

        sut.setCalenderTime(cal1,0,3,2,100);

        sut.setCalenderTime(cal12,5,7,8,100+600);

        Time diffTime2 = sut.getTimeDiff(cal1,cal12);
        Calendar diffTime2_1 = Calendar.getInstance();
        sut.getTimeDiff(cal1,cal12,diffTime2_1);
        assertEquals(5,diffTime2_1.get(Calendar.HOUR_OF_DAY));
        assertEquals(4,diffTime2_1.get(Calendar.MINUTE));
        assertEquals(6,diffTime2_1.get(Calendar.SECOND));
        assertEquals(600,diffTime2_1.get(Calendar.MILLISECOND));
        Time sum = sut.timeSum(diffTime,diffTime2);
        assertEquals((expectetDiffMs+5*60*60*1000+4*60*1000+6*1000+600),sum.getTime());

        Calendar sum2 = (Calendar) diffTime_1.clone();
        sut.timeSum(diffTime_1,diffTime2_1,sum2);
        assertEquals(5,sum2.get(Calendar.HOUR_OF_DAY));
        assertEquals(7,sum2.get(Calendar.MINUTE));
        assertEquals(8,sum2.get(Calendar.SECOND));
        assertEquals(700,sum2.get(Calendar.MILLISECOND));


    }



}
