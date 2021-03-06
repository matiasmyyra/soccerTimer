package com.gmail.koivisto.p.mika.soccertimer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CommonTestMethod {
    static int countPlayer = 0;
    public void addStartinPlayer8vs8(GameData sut, int[] tactic) {
        int numberOfPalyer = 0;
        for(int row = 0; row < tactic.length; row++) {
            for(int column = 0; column < tactic[row]; column++) {
                addOnePlayerPlayer(sut, row, column, false);
                numberOfPalyer++;
            }
        }
        assertEquals(8, numberOfPalyer);

    }
    public int[] setGameModeAndTacticTestData8vs8(GameData sut) {
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
    public void addOnePlayerPlayer(GameData sut, int row, int column, boolean exchangePlayer) {
        countPlayer++;
        Player p = new Player();
        getPlayerTestData("PlayerName_" + column + "_" + row + "_" + countPlayer, p);
        p.location.clear();
        addLocation(p,row,column);
        p.exchangePalyer = exchangePlayer;
        assertEquals(Status.NO_ERROR,sut.setPlayer(p));
    }
    public void addLocation(Player p, int row, int column) {
        LocationInTheFiled l = new LocationInTheFiled();
        l.playerLocationRow = row;
        l.playerLocationColumn = column;
        p.location.add(l);
    }
    public void getPlayerTestData(String name,Player p) {
        p.name = name;
        p.exchangePalyer =false;
        addLocation(p,0,0);

    }
    public Player getPlayerTestData(String name) {
        Player p = new Player();//todo: uusi totettus ei toimi Hox Player costrutoria muutettu korjaa
        p.name = name;
        p.exchangePalyer =false;
        addLocation(p,0,0);

        return p;
    }
    public boolean isPlayersSameExceptedGameTime(Player a , Player b) {
        boolean ret = true;
        if(a.name != b.name ||
        a.Injured != b.Injured ||
        a.exchangePalyer != b.exchangePalyer ||
        a.currentRow != b.currentRow ||
        a.location.size() != b.location.size()) {
            ret = false;
        }
        return ret;

    }
    public String setExchangePlayerThreeLocation02_12_22(GameData sut) {
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
        l3.playerLocationColumn = 2;
        l3.playerLocationRow = 2;
        location.add(l3);

        addOneLocationMultipleLocation(sut, location,0,2);
        addOneLocationMultipleLocation(sut, location,1,2);
        addOneLocationMultipleLocation(sut, location,2,2);

        return addOnePlayerMultibleLocation(sut, location, true);
    }

    private void addOneLocationMultipleLocation(GameData sut, ArrayList<LocationInTheFiled> location, int currentColumn, int currentRow) {
        sut.AddMoreLocationToFieldPlayer(currentColumn,currentRow,location);

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



}
