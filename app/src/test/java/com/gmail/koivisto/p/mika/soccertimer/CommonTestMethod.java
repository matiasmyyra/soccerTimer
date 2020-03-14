package com.gmail.koivisto.p.mika.soccertimer;

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
        Player p = new Player();
        p.name = name;
        p.exchangePalyer =false;
        addLocation(p,0,0);

        return p;
    }



}
