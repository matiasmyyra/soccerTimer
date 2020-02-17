package com.gmail.koivisto.p.mika.soccertimer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

enum GameMode {
    GAME_MODE_8VS8,
    GAME_MODE_5VS5,
    GAME_MODE_11VS11
}
public class GameData {
    GameMode gameMode;
    GameTactics gameTactics = new GameTactics();
    Date gameStartTime;
    Date gameHalfTimeStart;
    Date gameNextRoundTimeStart;
    Date gameStartEnd;
    int numOfPlayers;
    ArrayList<Player> players = new ArrayList<Player>();

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
    public static Date intialZeroTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public boolean setPlayer(Player p) {
        if(gameTactics.isValidRow(p.playerLocationRow)) {


            Date newDate = new Date();
            p.gameTime = Calendar.getInstance();
            p.gameTime.set(Calendar.HOUR_OF_DAY, 0);
            p.gameTime.set(Calendar.MINUTE, 0);
            p.gameTime.set(Calendar.SECOND, 0);
            p.gameTime.set(Calendar.MILLISECOND, 0);
            players.add(p);
            return true;
        }
        else{
            return false;
        }

    }
    public void setPlayerNames(String[] palayersName) {
        for (int i = 0; i < palayersName.length; i++) {
            Player p = new Player();
            p.name = palayersName[i];
            players.add(p);

        }
    }

    public Player getPlayer(String name) {
        for (int counter = 0; counter < players.size(); counter++) {
            if(players.get(counter).name == name) {
                return players.get(counter);
            }
        }
        return null;
    }
    public int getNumOfPlayers() {
        return players.size();
    }
}
