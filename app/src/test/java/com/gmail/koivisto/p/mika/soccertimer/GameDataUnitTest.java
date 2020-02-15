package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameDataUnitTest {
    @Test
    public void testGameModeSet() {
        GameData sut = new GameData();
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        assertEquals(GameMode.GAME_MODE_8VS8,sut.gameMode);
    }
    @Test
    public void testAddPlayer() {
        GameData sut = new GameData();
        int goalkeeper = 1;
        int[] tactic = {1, 3, 3, goalkeeper};
        sut.gameTactics.setNumLayers(tactic.length);
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        assertEquals(GameMode.GAME_MODE_8VS8,sut.gameMode);
        sut.gameTactics.setNumOfPlayerInTheLayers(tactic);
        //Missing check ...
    }

    @Test
    public void testAddOnePlayer() {
        GameData sut = new GameData();
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        int goalkeeper = 1;
        int[] tactic ={1,3,3,goalkeeper};
        //Row          3,2,1,    0
        //      ..............  Row
        //             0          3
        //        0    0    0     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        sut.gameTactics.setNumLayers(tactic.length);
        sut.gameTactics.setNumOfPlayerInTheLayers(tactic);
        //.....................................
        Player p = new Player();
        p.name = "Tommi P";
        p.exchangePalyer =false;
        p.playerLocationRow = 3;
        p.playerLocationColumn = 0;
        sut.setPlayer(p);

    }


}


