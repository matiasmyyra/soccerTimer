package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameDataUnitTest {
    @Test
    public void testGameModeSet() {
        GameData sut;
        sut = new GameData();
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        assertEquals(GameMode.GAME_MODE_8VS8,sut.gameMode);
    }
}
