package com.gmail.koivisto.p.mika.soccertimer;

import java.util.ArrayList;
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
    public void setPlayer(Player p) { players.add(p); }
    public void setPlayerNames(String[] palayersName) {
        for (int i = 0; i < palayersName.length; i++) {
            Player p = new Player();
            p.name = palayersName[i];
            players.add(p);

        }
    }
}
