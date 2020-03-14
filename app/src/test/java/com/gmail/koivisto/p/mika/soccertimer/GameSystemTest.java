package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    @Test
    public void testStartGameWithExchangePlayerAndUpdateTime() {
        CommonTestMethod com = new CommonTestMethod();
        GameData sut = new GameData();
        int timeToChangePlayer = 3;
        sut.setTimeToChangePlayetInMinutes(timeToChangePlayer);
        int[] tactic = com.setGameModeAndTacticTestData8vs8(sut);
        com.addStartinPlayer8vs8(sut, tactic);

        //      ..............  Row     exchange player
        //             0          3
        //        x    x    x     2           x
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        String fistExchangePlayerName =com.setExchangePlayerThreeLocation02_12_22(sut);

        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
        Calendar startTime = sut.startGame();
        GameTime time = new GameTime();
        Calendar zero = Calendar.getInstance();
        time.setCalenderTime(zero,0,0,0,0);
        for(Player p : sut.players) {
            assertEquals(p.gameTime, zero);
        }

        Calendar update_1Minute = Calendar.getInstance();
        time.setCalenderTime(update_1Minute,0,1,0,0);
        sut.upDateGameTimeToPlayer(update_1Minute);
        assertFalse(sut.isTimeToChangePlayewr());

        sut.upDateGameTimeToPlayer(update_1Minute);
        assertFalse(sut.isTimeToChangePlayer());
        sut.upDateGameTimeToPlayer(update_1Minute);
        assertTrue(sut.isTimeToChangePlayewr());

        ArrayList<String> playersWhoGoToField = sut.getNextPlayersToField();
        ArrayList<String> playersWhoComesFromFieldToRest = sut.getNextWhoComesFromFieldToRest();
        for(String name : playersWhoGoToField) {
            assertEquals(name,fistExchangePlayerName);
        }
        for(String name : playersWhoComesFromFieldToRest) {
            assertEquals(name,fistExchangePlayerName); //Todo: Hox Name not correct
        }

    }
}
