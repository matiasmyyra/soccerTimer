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
    public void testAddPalyer() {
        GameData sut = new GameData();
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        int goalkeeper = 1;
        int[] tactic ={1,3,3,goalkeeper};
        sut.gameTactics.setNumLayers(tactic.length);
        sut.gameTactics.setNumOfPlayerInTheLayers(tactic);
        Player p = new Player();
        p.name = "Tommi P";
        p.exchangePalyer =false;
        p.position = "mi??";
        sut.setPlayer(p);

    }
}


