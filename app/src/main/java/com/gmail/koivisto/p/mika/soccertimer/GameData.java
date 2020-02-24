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

    public Status setPlayer(Player p) {
        Status IsValid = Status.NO_ERROR;
        for (LocationInTheFiled l : p.location) {
            if(!gameTactics.isValidRow(l.playerLocationRow) ||
                    !gameTactics.isValidColumn(l.playerLocationRow,l.playerLocationColumn)) {
                IsValid = Status.PLAYER_ROW_OR_COLUMN_NOT_VALID;
            }
        }
        for (Player alreadyAddedPlayer : players) {
            if(alreadyAddedPlayer.name.compareTo(p.name) == 0) {
                IsValid = Status.SAME_NAME_IS_ALREADY_ADDED;
            }
        }

        if(IsValid == Status.NO_ERROR) {
            Date newDate = new Date();
            p.gameTime = Calendar.getInstance();
            p.gameTime.set(Calendar.HOUR_OF_DAY, 0);
            p.gameTime.set(Calendar.MINUTE, 0);
            p.gameTime.set(Calendar.SECOND, 0);
            p.gameTime.set(Calendar.MILLISECOND, 0);
            players.add(p);
            return IsValid;
        }
        else{
            return IsValid;
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

    public Status isStartingFieldSet() {
        final String TAG = "isStartingFieldSet";
        Status success = Status.NO_ERROR;
        outerloop:
        for(int row = 0; row < gameTactics.numOfPlayerInTheLayers.length; row++) {
            for(int column = 0; column < gameTactics.numOfPlayerInTheLayers[row]; column++) {
                int numOfPlayersStartingFieldInOneLocation = 0;

                for(Player p : players) {
                    for (LocationInTheFiled l : p.location) {
                        if (l.playerLocationColumn == column && l.playerLocationRow == row &&
                                p.exchangePalyer == false) {
                            numOfPlayersStartingFieldInOneLocation++;
                        }
                    }
                    for (LocationInTheFiled l : p.location) {
                        int numOfSameLocation = 0;
                        for (LocationInTheFiled l2 : p.location) {
                            if (l.playerLocationColumn == l2.playerLocationColumn && l.playerLocationRow == l2.playerLocationRow) {
                                numOfSameLocation++;
                            }
                        }
                        if(numOfSameLocation > 1) {
                            success = Status.DUBLICATE_LOCATION_IN_SAME_PLAYER;
                            MyLog.e(TAG,success.getDescription()+" Column:"+column+" Row:"+row);
                            break outerloop;
                        }

                    }


                }
                if(numOfPlayersStartingFieldInOneLocation > 1){
                    success = Status.DUPLICATE_PLAYER_IN_SAME_LOCATION;
                    MyLog.e(TAG,success.getDescription()+" Column:"+column+" Row:"+row);
                    break outerloop;

                }
                else if(numOfPlayersStartingFieldInOneLocation == 0) {
                    success = Status.NO_PLAYER_AT_ALL_IN_LOCATION;
                    MyLog.e(TAG,success.getDescription()+" Column:"+column+" Row:"+row);
                    break outerloop;
                }



            }
        }
        return  success;
    }

    public Status removePlayer(String name) {
        return Status.NOT_YET_IMPLEMENTED;
    }
}
