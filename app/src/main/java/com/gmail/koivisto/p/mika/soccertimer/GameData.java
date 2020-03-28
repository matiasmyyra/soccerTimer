package com.gmail.koivisto.p.mika.soccertimer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

enum GameMode {
    GAME_MODE_8VS8,
    GAME_MODE_5VS5,
    GAME_MODE_11VS11
}

public class GameData {
    GameMode gameMode;
    GameTactics gameTactics = new GameTactics();
    Calendar gameFirstRoundStartTime;
    Calendar gameCurrentTime;
    Calendar gameHalfTimeStart;
    Calendar gameNextRoundTimeStart;
    Calendar gameStartEnd;
    int numOfPlayers;
    GameTime timeService = new GameTime();
    ArrayList<Player> players = new ArrayList<Player>();

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public void setGameCurrentTime(Calendar gameCurrentTime) {
        this.gameCurrentTime = gameCurrentTime;
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
        Iterator itr = players.iterator();
        while (itr.hasNext())
        {
            Player x = (Player)itr.next();
            if (x.name.compareTo(name) == 0)
                itr.remove();
        }
        return Status.NO_ERROR;
    }
    private boolean findPlayer(String player1Name,Iterator itr) {
        boolean find = false;
        Iterator itrTemp= players.iterator();

        while (itrTemp.hasNext()) {
            Player x1 = (Player) itrTemp.next();
            if (x1.name.compareTo(player1Name) == 0) {
                find = true;
                break;
            }
            else
                itr.next();
        }
        return find;
    }
    public Status swapTwoPlayerLocation(String player1Name, String player2Name) {
        Status ret =Status.NO_ERROR;
        Iterator itr = players.iterator();;
        boolean findPlayer1 = findPlayer(player1Name,itr);
        Iterator itr2 =players.iterator();;
        boolean findPlayer2 = findPlayer(player2Name,itr2);
        if(findPlayer1 && findPlayer2){
            Player x1 = (Player) itr.next();
            Player x2 = (Player) itr2.next();
            ArrayList<LocationInTheFiled>  temp = x1.location;

            x1.location = x2.location;
            x2.location = temp;
        }
        else {

            ret = Status.NO_PLAYRR_FIND;
        }
        return  ret;

    }

    public Status removePlayersLocation(String name, LocationInTheFiled l2) {
        Status ret = Status.NO_ERROR;
        Iterator itr = players.iterator();
        boolean find = false;
        outerloop:
        while (itr.hasNext())
        {
            Player x = (Player)itr.next();
            if (x.name.compareTo(name) == 0) {
                Iterator itr2 = x.location.iterator();
                while (itr2.hasNext()) {
                    LocationInTheFiled l1 = (LocationInTheFiled) itr2.next();
                    if (l1 == l2) {
                        itr2.remove();
                        find = true;
                        break outerloop;
                    }
                }


            }
        }
        if(!find)
            ret = Status.NO_LOCATION_FIND;
        return ret;
    }

    public Status setPlayerNotInjured(String name) {
        return setInjured(name, false);
    }

    public Status setPlayerInjured(String name) {
        return setInjured(name, true);
    }

    private Status setInjured(String name, boolean b) {
        Status ret = Status.NO_ERROR;
        Iterator itr2 = players.iterator();
        ;
        findPlayer(name, itr2);
        if (findPlayer(name, itr2)) {
            Player x = (Player) itr2.next();
            x.Injured = b;
        } else
            ret = Status.NO_PLAYRR_FIND;

        return ret;
    }

    public Calendar startGame() {
        gameFirstRoundStartTime =Calendar.getInstance();
        gameCurrentTime = (Calendar) gameFirstRoundStartTime.clone();
        for(Player p : players) {
            p.gameTime = (Calendar) gameFirstRoundStartTime.clone();
            timeService.setCalenderTime(p.gameTime,0,0,0,0);
        }

        return  gameFirstRoundStartTime;
    }

    public void upDateGameTimeToPlayer(Calendar update) {
        timeService.timeSum(p.gameCurrentTime, update, p.gameCurrentTime);
        for(Player p : players) {
            if(p.exchangePalyer == false && (p.Injured == null || p.Injured == false ) ) {
                timeService.timeSum(p.gameTime, update, p.gameTime);
            }
        }
    }

    public void setTimeToChangePlayerInMinutes(int timeToChangePlayer) {
    }

    public boolean isTimeToChangePlayer() {
        return false;
    }

    public ArrayList<String> getNextPlayersToField() {
        ArrayList<String> ret = null;
        return ret;
    }

    public ArrayList<String> getNextWhoComesFromFieldToRest() {
        ArrayList<String> ret = null;
        return ret;
    }
}
