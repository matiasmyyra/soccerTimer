package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class GameSystemTest {
    @Test
    public void testStartGameWithoutExchangePlayerAndUpdateTime() {
        GameData sut = new GameData();
        CommonTestMethod com = new CommonTestMethod();
        int[] tactic = com.setGameModeAndTacticTestData8vs8(sut);
        com.addStartinPlayer8vs8(sut, tactic);

        //      ..............  Row
        //             0          3
        //        0    0    0     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
        Calendar startTime = sut.startGame();
        GameTime time = new GameTime();
        Calendar zero = Calendar.getInstance();
        time.setCalenderTime(zero,0,0,0,0);
        for(Player p : sut.players) {
            assertEquals(p.gameTime, zero);
        }

        Calendar firstUpdate = Calendar.getInstance();
        time.setCalenderTime(firstUpdate,0,0,10,0);
        sut.upDateGameTimeToPlayer(firstUpdate);
        for(Player p : sut.players) {
            Calendar expectedCurrentTime = Calendar.getInstance();
            time.timeSum(zero,firstUpdate,expectedCurrentTime);
            assertEquals(0,p.gameTime.get(Calendar.HOUR_OF_DAY));
            assertEquals(0,p.gameTime.get(Calendar.MINUTE));
            assertEquals(10,p.gameTime.get(Calendar.SECOND));
            assertEquals(0,p.gameTime.get(Calendar.MILLISECOND));
            //assertEquals(p.gameTime, expectedCurrentTime);
        }
        Calendar SecondUpdate = Calendar.getInstance();
        time.setCalenderTime(SecondUpdate,1,5,10,33);
        sut.upDateGameTimeToPlayer(SecondUpdate);
        for(Player p : sut.players) {
            Calendar expectedCurrentTime = Calendar.getInstance();
            time.timeSum(firstUpdate,SecondUpdate,expectedCurrentTime);
            //assertEquals(p.gameTime, expectedCurrentTime);
            assertEquals(1,p.gameTime.get(Calendar.HOUR_OF_DAY));
            assertEquals(5,p.gameTime.get(Calendar.MINUTE));
            assertEquals(20,p.gameTime.get(Calendar.SECOND));
            assertEquals(33,p.gameTime.get(Calendar.MILLISECOND));
        }






    }
}
