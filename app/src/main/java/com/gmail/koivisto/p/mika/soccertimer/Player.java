package com.gmail.koivisto.p.mika.soccertimer;

import java.util.ArrayList;
import java.util.Calendar;

public class Player {
    public Player(Player p) {
        this.currentRow = p.currentRow;
        this.currentColumn = p.currentColumn;
        this.exchangePalyer = p.exchangePalyer;
        this.Injured = p.Injured;
        this.name = p.name;
        if(p.gameTime != null)
            this.gameTime = (Calendar) p.gameTime.clone();
        if(p.location != null)
        this.location = (ArrayList<LocationInTheFiled>)p.location.clone();
    }
    public Player() {

    }
    public int currentRow;
    public int currentColumn;
    String name;
    Calendar gameTime;
    ArrayList<LocationInTheFiled> location = new ArrayList<LocationInTheFiled>();
    Boolean exchangePalyer;
    Boolean Injured;


}
//Row =   playerLocationRow
//Colunm =playerLocationColumn;
//      ..............  Row
//             0          3
//        0    0    0     2
//        0    0    0     1
//
//             0          0
//      ......___......
//column  0    1    2