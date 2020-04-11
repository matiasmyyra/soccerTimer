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
        assertEquals(sut.gameCurrentTime, sut.gameFirstRoundStartTime);
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
        sut.setTimeToChangePlayerInMinutes(timeToChangePlayer);
        int[] tactic = com.setGameModeAndTacticTestData8vs8(sut);
        com.addStartinPlayer8vs8(sut, tactic);

        //      ..............  Row     exchange player
        //             0          3
        //        a    b    c     2           x=playersExchange-->a,b,c
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        String fistExchangePlayerName =com.setExchangePlayerThreeLocation02_12_22(sut);
        Player a = sut.getPlayerDataFromLocation(2,0);
        Player b = sut.getPlayerDataFromLocation(2,1);
        Player c = sut.getPlayerDataFromLocation(2,2);
        ArrayList<Player> playersExchange = sut.getExchangePlayerList();
        assertEquals(1,playersExchange.size());
        Player x = playersExchange.get(0);
        assertEquals(fistExchangePlayerName,x.name);


        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
        Calendar startTime = sut.startGame();
        GameTime time = new GameTime();
        ckeckThatAllPlayerTimeIsSetToZero(sut, time);

        //Column 0
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,true);
        CheckThatPlayerChangeDoCorrectly(sut, fistExchangePlayerName, a.name);

        //Column 1
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,true);
        CheckThatPlayerChangeDoCorrectly(sut, a.name, b.name);

        //Column 2
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,1, time,true);
        CheckThatPlayerChangeDoCorrectly(sut, b.name, c.name);

        //Column 3
        upDateAndCheckCurrentTimeWithXMinutes(sut,10, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,11, time,false);
        upDateAndCheckCurrentTimeWithXMinutes(sut,12, time,true);
        CheckThatPlayerChangeDoCorrectly(sut, c.name, fistExchangePlayerName);

    }

    private void CheckThatPlayerChangeDoCorrectly(GameData sut, String exchangePlayerName, String playerNameWhoComeToGame) {
        ArrayList<Player> playersWhoGoToField = sut.getNextPlayersToField();
        assertEquals(1,playersWhoGoToField.size());
        ArrayList<Player> playersWhoComesFromFieldToRest = sut.getNextWhoComesFromFieldToRest();
        assertEquals(1,playersWhoComesFromFieldToRest.size());
        for(Player p : playersWhoGoToField) {
            assertEquals(p.name,exchangePlayerName);
        }
        for(Player p : playersWhoComesFromFieldToRest) {
            assertEquals(p.name,playerNameWhoComeToGame);
        }
        sut.doPlayersChange();
    }

    private void upDateAndCheckCurrentTimeWithXMinutes(GameData sut,int minutes, GameTime time, boolean isTimeToChangePlayer) {
        Calendar update_1Minute = Calendar.getInstance();
        int currentTimeMin = sut.gameCurrentTime.get(Calendar.MINUTE);
        time.setCalenderTime(update_1Minute,0,minutes,0,0);
        sut.upDateGameTimeToPlayer(update_1Minute);
        assertEquals(sut.gameCurrentTime.get(Calendar.MINUTE), currentTimeMin+1);
        assertEquals(isTimeToChangePlayer ,sut.isTimeToChangePlayer());

    }

    private void ckeckThatAllPlayerTimeIsSetToZero(GameData sut, GameTime time) {
        Calendar zero = Calendar.getInstance();
        time.setCalenderTime(zero,0,0,0,0);
        for(Player p : sut.players) {
            assertEquals(p.gameTime, zero);
        }
    }
}
