package com.gmail.koivisto.p.mika.soccertimer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


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
        Player p = getPlayerTestData("Tintti Tiipetissä");
        sut.setPlayer(p);
        assertEquals(p,sut.getPlayer(p.name));
        assertNull(sut.getPlayer("Taavi joka ei ole joukkueessa"));
    }

    @Test
    public void testAddTwoPlayerAndRemoveOnePlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        Player p = getPlayerTestData("Teppo Tattimaa");
        sut.setPlayer(p);
        assertEquals(1,sut.getNumOfPlayers());
        Player p2 = getPlayerTestData("Tiitinen Seppo");
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
        //        0    P2    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2

        Player p1 = new Player();
        getPlayerTestData(player1Name,p1);

        p1.location.clear();
        int p1_row = 2;
        int p1_column = 2;
        addLocation(p1,p1_row,p1_column);
        assertEquals(Status.NO_ERROR, sut.setPlayer(p1));

        Player p2 = new Player();
        getPlayerTestData(player2Name,p2);

        p2.location.clear();
        int p2_row = 1;
        int p2_column = 1;
        addLocation(p2,p2_row,p2_column);
        assertEquals(Status.NO_ERROR, sut.setPlayer(p2));

        assertEquals(2,sut.getNumOfPlayers());

        assertEquals(Status.NO_ERROR,sut.swapTwoPlayerLocation(player1Name,player2Name));

        Player newP1 = getPlayerTestData(player1Name);
        Player newP2 = getPlayerTestData(player2Name);

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
        setGameModeAndTacticTestData8vs8(sut);
        Player p = getPlayerTestData("Teppo Tattimaa");
        assertEquals(Status.NO_ERROR, sut.setPlayer(p));
        assertEquals(1,sut.getNumOfPlayers());
        Player p2 = getPlayerTestData("Teppo Tattimaa");
        assertEquals(Status.SAME_NAME_IS_ALREADY_ADDED,sut.setPlayer(p2));
        assertEquals(1,sut.getNumOfPlayers());


    }
    @Test
    public void testInvalidRowParameterPlayer() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        Player p = new Player();
        getPlayerTestData("Teppo Tattimaa",p);
        p.location.clear();
        addLocation(p,tactic.length,0);
        assertEquals(Status.PLAYER_ROW_OR_COLUMN_NOT_VALID,sut.setPlayer(p));
        assertEquals(0,sut.getNumOfPlayers());


    }
    @Test
    public void testInvalidColumParameterPlayer() {
        GameData sut = new GameData();
        setGameModeAndTacticTestData8vs8(sut);
        Player p = new Player();
        getPlayerTestData("Teppo Tattimaa",p);
        p.location.clear();
        addLocation(p,0,100);
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
        getPlayerTestData("PlayerName_"+column+"_"+row, duplicatedPlayer);
        duplicatedPlayer.location.clear();
        addLocation(duplicatedPlayer,row,column);
        duplicatedPlayer.exchangePalyer = false;
        assertEquals(Status.NO_ERROR,sut.setPlayer(duplicatedPlayer));
        assertEquals(Status.DUPLICATE_PLAYER_IN_SAME_LOCATION,sut.isStartingFieldSet());
    }
    @Test
    public void testAddStartingPlayer8vs8AndOnrExchangePlayersInSingleLocation() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        addOnePlayerPlayer(sut, 0, 0, true);
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

        addOnePlayerMultibleLocation(sut,location, true);
        assertEquals(Status.NO_ERROR,sut.isStartingFieldSet());
    }



    @Test
    public void testAddStartingPlayer8vs8AndOarExchangePlayersInTwoLocationAndOneIsDuplicate() {
        GameData sut = new GameData();
        int[] tactic = setGameModeAndTacticTestData8vs8(sut);
        addStartinPlayer8vs8(sut, tactic);
        ArrayList<LocationInTheFiled> location = new ArrayList<LocationInTheFiled>();
        //      ..............  Row
        //             0          3
        //        x    x    x     2
        //        0    0    0     1
        //
        //             0          0
        //      ......___......
        //column  0    1    2

        LocationInTheFiled l1 =new LocationInTheFiled();
        l1.playerLocationColumn = 0;
        l1.playerLocationRow = 2;
        location.add(l1);
        LocationInTheFiled l2 =new LocationInTheFiled();
        l2.playerLocationColumn = 1;
        l2.playerLocationRow = 2;
        location.add(l2);
        LocationInTheFiled l3 =new LocationInTheFiled();
        l3.playerLocationColumn = 1;
        l3.playerLocationRow = 2;
        location.add(l3);
        addOnePlayerMultibleLocation(sut,location, true);
        assertEquals(Status.DUBLICATE_LOCATION_IN_SAME_PLAYER,sut.isStartingFieldSet());
    }

    private void addOnePlayerMultibleLocation(GameData sut, ArrayList<LocationInTheFiled> location, boolean exchangePalyer) {
        countPlayer++;
        Player p = new Player();
        getPlayerTestData("PlayerName_" + countPlayer, p);
        p.location =location;
        p.exchangePalyer = exchangePalyer;
        assertEquals(Status.NO_ERROR,sut.setPlayer(p));
    }

    private void addOnePlayerPlayer(GameData sut, int row, int column, boolean exchangePlayer) {
        countPlayer++;
        Player p = new Player();
        getPlayerTestData("PlayerName_" + column + "_" + row + "_" + countPlayer, p);
        p.location.clear();
        addLocation(p,row,column);
        p.exchangePalyer = exchangePlayer;
        assertEquals(Status.NO_ERROR,sut.setPlayer(p));
    }

    private void addStartinPlayer8vs8(GameData sut, int[] tactic) {
        int numberOfPalyer = 0;
        for(int row = 0; row < tactic.length; row++) {
            for(int column = 0; column < tactic[row]; column++) {
                addOnePlayerPlayer(sut, row, column, false);
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

    private Player getPlayerTestData(String name) {
        Player p = new Player();
        p.name = name;
        p.exchangePalyer =false;
        addLocation(p,0,0);

        return p;
    }

    private void addLocation(Player p, int row, int column) {
        LocationInTheFiled l = new LocationInTheFiled();
        l.playerLocationRow = row;
        l.playerLocationColumn = column;
        p.location.add(l);
    }

    private void getPlayerTestData(String name,Player p) {
        p.name = name;
        p.exchangePalyer =false;
        addLocation(p,0,0);

    }


}


