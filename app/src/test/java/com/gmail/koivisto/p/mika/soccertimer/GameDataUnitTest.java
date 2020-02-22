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
    public void testAddGameTactic() {
        GameData sut = new GameData();
        int goalkeeper = 1;
        int[] tactic = {1, 3, 3, goalkeeper};
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        assertEquals(GameMode.GAME_MODE_8VS8,sut.gameMode);
        sut.gameTactics.setNumOfPlayerInTheLayers(tactic);
        assertEquals(sut.gameTactics.getNumLayers(),tactic.length);

    }

    @Test
    public void testAddOnePlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData(sut);
        Player p = getPlayerTestData("Tintti Tiipetissä");
        sut.setPlayer(p);
        assertEquals(p,sut.getPlayer(p.name));
        assertEquals(null,sut.getPlayer("Taavi joka ei ole joukkueessa"));
    }

    @Test
    public void testAddTwoPlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData(sut);
        Player p = getPlayerTestData("Teppo Tattimaa");
        sut.setPlayer(p);
        assertEquals(1,sut.getNumOfPlayers());
        Player p2 = getPlayerTestData("Tiitinen Seppo");
        sut.setPlayer(p2);
        assertEquals(2,sut.getNumOfPlayers());
        assertEquals(p,sut.getPlayer(p.name));
        assertEquals(p2,sut.getPlayer(p2.name));

        assertEquals(null,sut.getPlayer("Taavi joka ei ole joukkueessa"));

    }
    @Test
    public void testInvalidRowParameterPlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData(sut);
        Player p = new Player();
        getPlayerTestData("Teppo Tattimaa",p);
        p.playerLocationRow = tactic.length;
        assertEquals(false,sut.setPlayer(p));
        assertEquals(0,sut.getNumOfPlayers());


    }
    @Test
    public void testInvalidColumParameterPlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData(sut);
        Player p = new Player();
        getPlayerTestData("Teppo Tattimaa",p);
        p.playerLocationColumn = 1;
        assertEquals(false,sut.setPlayer(p));
        assertEquals(0,sut.getNumOfPlayers());


    }
    @Test
    public void testAddStartingPlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData(sut);
        int numberOfPalyer = 0;
        for(int row = 0; row < tactic.length; row++) {
            for(int column = 0; column < tactic[row]; column++) {
                Player p = new Player();
                getPlayerTestData("PlayerName_"+column+"_"+row, p);
                p.playerLocationColumn = column;
                p.playerLocationRow = row;
                p.exchangePalyer = false;
                assertEquals(true, sut.setPlayer(p));
                numberOfPalyer++;
            }
        }
        assertEquals(8, numberOfPalyer);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }

    private int[]  setGameModeAndTacticTestData(GameData sut) {
        sut.setGameMode(GameMode.GAME_MODE_8VS8);
        int goalkeeper = 1;
        int[] tactic ={1,3,3,goalkeeper};
        //      ..............  Row
        //             0          3
        //        0    0    0     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        sut.gameTactics.setNumOfPlayerInTheLayers(tactic);
        return tactic;
    }

    private Player getPlayerTestData(String name) {
        Player p = new Player();
        p.name = name;
        p.exchangePalyer =false;
        p.playerLocationRow = 3;
        p.playerLocationColumn = 0;
        return p;
    }
    private void getPlayerTestData(String name,Player p) {
        p.name = name;
        p.exchangePalyer =false;
        p.playerLocationRow = 3;
        p.playerLocationColumn = 0;

    }


}


