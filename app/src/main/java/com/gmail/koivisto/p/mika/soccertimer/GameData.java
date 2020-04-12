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
enum PlayerChangeState {
    IS_TIME_TO_CHANGE_PLAYER
}

public class GameData {
    PlayerChangeState playerChangeState;
    GameMode gameMode;
    GameTactics gameTactics = new GameTactics();
    Calendar gameFirstRoundStartTime;
    Calendar gameCurrentTime;
    Calendar lastPlayerIsChangedTime;
    Calendar gameHalfTimeStart;
    Calendar gameNextRoundTimeStart;
    Calendar gameStartEnd;
    int numOfPlayers;
    GameTime timeService = new GameTime();
    ArrayList<Player> players = new ArrayList<Player>();
    private int timeToChangePlayerMin;

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
            Player x = new Player(p);
            x.currentColumn = p.location.get(0).playerLocationColumn;
            x.currentRow = p.location.get(0).playerLocationRow;

            x.gameTime = Calendar.getInstance();
            x.gameTime.set(Calendar.HOUR_OF_DAY, 0);
            x.gameTime.set(Calendar.MINUTE, 0);
            x.gameTime.set(Calendar.SECOND, 0);
            x.gameTime.set(Calendar.MILLISECOND, 0);
            players.add(x);
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
/*
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

 */
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
        lastPlayerIsChangedTime = (Calendar) gameFirstRoundStartTime.clone();
        timeService.setCalenderTime(gameCurrentTime,0,0,0,0);
        timeService.setCalenderTime(lastPlayerIsChangedTime,0,0,0,0);
        for(Player p : players) {
            p.gameTime = (Calendar) gameFirstRoundStartTime.clone();
            timeService.setCalenderTime(p.gameTime,0,0,0,0);
        }

        return  gameFirstRoundStartTime;
    }

    public void upDateGameTimeToPlayer(Calendar update) {
        int hours = gameCurrentTime.get(Calendar.HOUR_OF_DAY);
        int min = gameCurrentTime.get(Calendar.MINUTE);
        int sec = gameCurrentTime.get(Calendar.SECOND);
        int millSec = gameCurrentTime.get(Calendar.MILLISECOND);
        int hours2 = gameCurrentTime.get(Calendar.HOUR_OF_DAY);
        int min2 = gameCurrentTime.get(Calendar.MINUTE);
        int sec2 = gameCurrentTime.get(Calendar.SECOND);
        int millSec2 = gameCurrentTime.get(Calendar.MILLISECOND);
        timeService.timeSum(gameCurrentTime, update, gameCurrentTime);
        hours = gameCurrentTime.get(Calendar.HOUR_OF_DAY);
        min = gameCurrentTime.get(Calendar.MINUTE);
        sec = gameCurrentTime.get(Calendar.SECOND);
        millSec = gameCurrentTime.get(Calendar.MILLISECOND);
        hours2 = gameCurrentTime.get(Calendar.HOUR_OF_DAY);
        min2 = gameCurrentTime.get(Calendar.MINUTE);
        sec2 = gameCurrentTime.get(Calendar.SECOND);
        millSec2 = gameCurrentTime.get(Calendar.MILLISECOND);
        for(Player p : players) {
            if(p.exchangePalyer == false && (p.Injured == null || p.Injured == false ) ) {


                timeService.timeSum(p.gameTime, update, p.gameTime);

            }
        }
    }

    public void setTimeToChangePlayerInMinutes(int timeToChangePlayer) {
        timeToChangePlayerMin = timeToChangePlayer;
    }

    public boolean isTimeToChangePlayer() {
        boolean ret = false;
        int sec = gameCurrentTime.get(Calendar.SECOND);
        int min = gameCurrentTime.get(Calendar.MINUTE);
        int ho = gameCurrentTime.get(Calendar.HOUR);

        long playTimeSeconds = (gameCurrentTime.get(Calendar.SECOND) +
                gameCurrentTime.get(Calendar.MINUTE) * 60 +
                gameCurrentTime.get(Calendar.HOUR) * 3600);

        long lastChangeTimeSeconds = (lastPlayerIsChangedTime.get(Calendar.SECOND) +
                lastPlayerIsChangedTime.get(Calendar.MINUTE) * 60 +
                lastPlayerIsChangedTime.get(Calendar.HOUR) * 3600);

        if(timeToChangePlayerMin*60 <= (playTimeSeconds-lastChangeTimeSeconds) ) {
            ret = true;
            playerChangeState = PlayerChangeState.IS_TIME_TO_CHANGE_PLAYER;
        }

        return ret;
    }

    public ArrayList<Player> getNextPlayersToField() {
        ArrayList<Player> ret = new ArrayList<Player>();

        for(Player p : players) {
            if(p.exchangePalyer == true ) {
                ret.add(p);
            }
        }

        return ret;
    }

    public ArrayList<Player> getNextWhoComesFromFieldToRest() {
        ArrayList<Player> exPlayers = getNextPlayersToField();
        ArrayList<Player> ret = new ArrayList<Player>();
        ArrayList<Player> ret2 = new ArrayList<Player>();
        ArrayList<LocationInTheFiled> tempLoc = new ArrayList<LocationInTheFiled>();
        for (Player p : exPlayers) {
            if (p.location.size() > 0) {
                tempLoc.addAll(p.location);
            }
        }
        for (LocationInTheFiled l : tempLoc) {
            for (Player p : players) {
                outerloop:
                if (p.exchangePalyer == false) {
                    for (LocationInTheFiled pl : p.location) {
                        if (pl.playerLocationRow == l.playerLocationRow &&
                                pl.playerLocationColumn == l.playerLocationColumn) {
                            ret.add(p);
                            break outerloop;
                        }


                    }
                }

            }


        }
        boolean findPlayer = true;
        for (Player p : ret) {
            findPlayer = true;
            for (Player p2 : ret) {
                if (p2.name != p.name) {
                    if (0 > p.gameTime.compareTo(p2.gameTime)) {
                        findPlayer = false;
                        break;
                    }
                }

            }
            if (findPlayer) {
                ret2.add(p);
                Iterator itr = ret.iterator();
                while (itr.hasNext()) {
                    Player x = (Player) itr.next();
                    if (x.name.compareTo(p.name) == 0)
                        itr.remove();
                }
            }


            if (exPlayers.size() == ret2.size()) {
                break;
            }
        }
        return ret2;
    }

    public Player getPlayerDataFromLocation(int row, int column) {
        for(Player p : players) {
            if(p.exchangePalyer == false && p.currentRow == row && p.currentColumn == column) {
                return p;
            }
        }
        return null;

    }

    public ArrayList<Player> getExchangePlayerList() {
        ArrayList<Player> ret = new ArrayList<Player>();
        for(Player p : players) {
            if(p.exchangePalyer == true) {
                ret.add(p);
            }
        }

        return ret;
    }

    public void doPlayersChange(String exchangePlayerName, String playerNameWhoComeToGame) {
        //TODO:Missing implementation
        ArrayList<LocationInTheFiled> location = new ArrayList<LocationInTheFiled>();
        int row = 0;
        int column = 0;
        for(Player p : players) {
            if(p.name == playerNameWhoComeToGame) {
                p.exchangePalyer = false;
                location.addAll(p.location);
                break;
            }
        }
        for(Player p : players) {
            if(p.name == exchangePlayerName) {
                p.exchangePalyer = true;
                p.location.clear();
                row = p.currentRow;
                column = p.currentColumn;
                p.location.addAll(location);
                break;
            }
        }
        for(Player p : players) {
            if(p.name == playerNameWhoComeToGame) {
                p.currentRow = row;
                p.currentColumn = column;
            }
        }
        lastPlayerIsChangedTime = (Calendar) gameCurrentTime.clone();


    }
}
