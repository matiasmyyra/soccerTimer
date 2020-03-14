package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class GameDataUnitTest {

    private static int countPlayer =0;

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
        setGameModeAndTacticTestData8vs8(sut);
        CommonTestMethod com = new CommonTestMethod();
        Player p = com.getPlayerTestData("Tintti Tiipetissä");
        sut.setPlayer(p);
        assertEquals(p,sut.getPlayer(p.name));
        assertNull(sut.getPlayer("Taavi joka ei ole joukkueessa"));
    }
    @Test
    public void markThePlayerAsInjured() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        CommonTestMethod com = new CommonTestMethod();
        Player p = com.getPlayerTestData("Tintti Tiipetissä");
        sut.setPlayer(p);
        assertEquals(p,sut.getPlayer(p.name));
        assertEquals(Status.NO_ERROR,sut.setPlayerInjured(p.name));
        Player pp1 = sut.getPlayer(p.name);
        assertTrue(pp1.Injured);
        assertEquals(Status.NO_ERROR,sut.setPlayerNotInjured(p.name));
        Player pp2 = sut.getPlayer(p.name);
        assertFalse(pp2.Injured);
    }

    @Test
    public void testAddTwoPlayerAndRemoveOnePlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        CommonTestMethod com = new CommonTestMethod();
        Player p = com.getPlayerTestData("Teppo Tattimaa");
        sut.setPlayer(p);
        assertEquals(1,sut.getNumOfPlayers());
        Player p2 = com.getPlayerTestData("Tiitinen Seppo");
        sut.setPlayer(p2);
        assertEquals(2,sut.getNumOfPlayers());
        assertEquals(p,sut.getPlayer(p.name));
        assertEquals(p2,sut.getPlayer(p2.name));
        assertNull(sut.getPlayer("Taavi joka ei ole joukkueessa"));
        assertEquals(Status.NO_ERROR,sut.removePlayer("Teppo Tattimaa"));
        assertEquals(1,sut.getNumOfPlayers());

    }
    @Test
    public void testAddTwoPlayerAndSwapLocation() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        String player1Name = "Teppo Tattimaa";
        String player2Name = "Tiitinen Seppo";

        //      ..............  Row
        //             0          3
        //        0    0    P1    2
        //        0    P2    0    1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        CommonTestMethod com = new CommonTestMethod();
        Player p1 = new Player();
        com.getPlayerTestData(player1Name,p1);

        p1.location.clear();
        int p1_row = 2;
        int p1_column = 2;
        com.addLocation(p1,p1_row,p1_column);
        assertEquals(Status.NO_ERROR, sut.setPlayer(p1));

        Player p2 = new Player();
        com.getPlayerTestData(player2Name,p2);

        p2.location.clear();
        int p2_row = 1;
        int p2_column = 1;
        com.addLocation(p2,p2_row,p2_column);
        assertEquals(Status.NO_ERROR, sut.setPlayer(p2));

        assertEquals(2,sut.getNumOfPlayers());

        assertEquals(Status.NO_ERROR,sut.swapTwoPlayerLocation(player1Name,player2Name));

        Player newP1 = com.getPlayerTestData(player1Name);
        Player newP2 = com.getPlayerTestData(player2Name);

        assertFalse(newP1.location.contains(p2.location));
        assertFalse(newP2.location.contains(p1.location));
        //      ..............  Row
        //             0          3
        //        0    0    P2    2
        //        0    P1   0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
    }
    @Test
    public void testAddDublicateName() {
        GameData sut = new GameData();
        CommonTestMethod com = new CommonTestMethod();
        setGameModeAndTacticTestData8vs8(sut);
        Player p = com.getPlayerTestData("Teppo Tattimaa");
        assertEquals(Status.NO_ERROR, sut.setPlayer(p));
        assertEquals(1,sut.getNumOfPlayers());
        Player p2 = com.getPlayerTestData("Teppo Tattimaa");
        assertEquals(Status.SAME_NAME_IS_ALREADY_ADDED,sut.setPlayer(p2));
        assertEquals(1,sut.getNumOfPlayers());


    }
    @Test
    public void testInvalidRowParameterPlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        Player p = new Player();
        CommonTestMethod com = new CommonTestMethod();
        com.getPlayerTestData("Teppo Tattimaa",p);
        p.location.clear();
        com.addLocation(p,tactic.length,0);
        assertEquals(Status.PLAYER_ROW_OR_COLUMN_NOT_VALID,sut.setPlayer(p));
        assertEquals(0,sut.getNumOfPlayers());


    }
    @Test
    public void testInvalidColumParameterPlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        Player p = new Player();
        CommonTestMethod com = new CommonTestMethod();
        com.getPlayerTestData("Teppo Tattimaa",p);
        p.location.clear();
        com.addLocation(p,0,100);
        assertEquals(Status.PLAYER_ROW_OR_COLUMN_NOT_VALID,sut.setPlayer(p));
        assertEquals(0,sut.getNumOfPlayers());


    }
    @Test
    public void testAddStartingPlayer8vs8() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }
    @Test
    public void testAddStartingPlayer8vs8AndRemoveOnePlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }
    @Test
    public void testAddDuplicateStartingPlayer8vs8() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        Player duplicatedPlayer = new Player();
        int row = 0;
        int column = 0;
        CommonTestMethod com = new CommonTestMethod();
        com.getPlayerTestData("PlayerName_"+column+"_"+row, duplicatedPlayer);
        duplicatedPlayer.location.clear();
        com.addLocation(duplicatedPlayer,row,column);
        duplicatedPlayer.exchangePalyer = false;
        assertEquals(Status.NO_ERROR,sut.setPlayer(duplicatedPlayer));
        assertEquals(Status.DUPLICATE_PLAYER_IN_SAME_LOCATION,sut.isStartingFieldSet());
    }
    @Test
    public void testAddStartingPlayer8vs8AndOnrExchangePlayersInSingleLocation() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        CommonTestMethod com = new CommonTestMethod();
        com.addOnePlayerPlayer(sut, 0, 0, true);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }

    @Test
    public void testAddStartingPlayer8vs8AndOnrExchangePlayersInThreeLocation() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);

        //      ..............  Row
        //             0          3
        //        x    x    x     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        setExchangePlayerThreeLocation02_12_22(sut, 2);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }

    private void setExchangePlayerThreeLocation02_12_22(GameData sut, int row2Column) {
        ArrayList<LocationInTheFiled> location = new ArrayList<LocationInTheFiled>();
        LocationInTheFiled l1 = new LocationInTheFiled();
        l1.playerLocationColumn = 0;
        l1.playerLocationRow = 2;
        location.add(l1);
        LocationInTheFiled l2 = new LocationInTheFiled();
        l2.playerLocationColumn = 1;
        l2.playerLocationRow = 2;
        location.add(l2);
        LocationInTheFiled l3 = new LocationInTheFiled();
        l3.playerLocationColumn = row2Column;
        l3.playerLocationRow = 2;
        location.add(l3);

        addOnePlayerMultibleLocation(sut, location, true);
    }

    @Test
    public void testAddStartingPlayer8vs8AndOExchangePlayersInThreeLocationAfterThatRemoveOneLocation() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);

        //      ..............  Row
        //             0          3
        //        x    x    x     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2
        ArrayList<LocationInTheFiled> location = new ArrayList<LocationInTheFiled>();
        LocationInTheFiled l1 =new LocationInTheFiled();
        l1.playerLocationColumn = 0;
        l1.playerLocationRow = 2;
        location.add(l1);
        LocationInTheFiled l2 =new LocationInTheFiled();
        l2.playerLocationColumn = 1;
        l2.playerLocationRow = 2;
        location.add(l2);
        LocationInTheFiled l3 =new LocationInTheFiled();
        l3.playerLocationColumn = 2;
        l3.playerLocationRow = 2;
        location.add(l3);

        String name = addOnePlayerMultibleLocation(sut,location, true);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
        assertEquals(Status.NO_ERROR,sut.removePlayersLocation(name,l2));
        Player pp = sut.getPlayer(name);
        assertEquals(2,pp.location.size());
        assertEquals(2,pp.location.get(0).playerLocationRow);
        assertEquals(0,pp.location.get(0).playerLocationColumn);
        assertEquals(2,pp.location.get(1).playerLocationRow);
        assertEquals(2,pp.location.get(1).playerLocationColumn);

    }
    @Test
    public void testAddStartingPlayer8vs8AndOarExchangePlayersInTwoLocationAndOneIsDuplicate() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        setExchangePlayerThreeLocation02_12_22(sut, 1);
        assertEquals(Status.DUBLICATE_LOCATION_IN_SAME_PLAYER,sut.isStartingFieldSet());
    }

    private String addOnePlayerMultibleLocation(GameData sut, ArrayList<LocationInTheFiled> location, boolean exchangePalyer) {
        countPlayer++;
        Player p = new Player();
        String name = "PlayerName_" + countPlayer;
        CommonTestMethod com = new CommonTestMethod();
        com.getPlayerTestData(name, p);
        p.location =location;
        p.exchangePalyer = exchangePalyer;
        assertEquals(Status.NO_ERROR,sut.setPlayer(p));
        return name;
    }

    private void addStartinPlayer8vs8(GameData sut, int[] tactic) {
        int numberOfPalyer = 0;
        for(int row = 0; row < tactic.length; row++) {
            for(int column = 0; column < tactic[row]; column++) {
                CommonTestMethod com = new CommonTestMethod();
                com.addOnePlayerPlayer(sut, row, column, false);
                numberOfPalyer++;
            }
        }
        assertEquals(8, numberOfPalyer);
    }


    private int[] setGameModeAndTacticTestData8vs8(GameData sut) {
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
}


