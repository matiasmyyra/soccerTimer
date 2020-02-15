package com.gmail.koivisto.p.mika.soccertimer;

import java.util.Calendar;

public class Player {
    String name;
    Calendar gameTime;
    int playerLocationRow;
    int playerLocationColumn;
    Boolean exchangePalyer;
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