package com.gmail.koivisto.p.mika.soccertimer;
// e.g. int[] tactic ={1,3,3,goalkeeper};    1-3-3  8vs8
//      ..............  Row
//             0          3
//        0    0    0     2
//        0    0    0     1
//
//             0          0
//      ......___......
//column  0    1    2

public class GameTactics {
    int numLayers;
    int[] numOfPlayerInTheLayers;

    public int[] getNumOfPlayerInTheLayers() {
        return numOfPlayerInTheLayers;
    }

    public int getNumLayers() {
        return numLayers;
    }

    public void setNumOfPlayerInTheLayers(int[] numOfPlayerInTheLayers) {
        this.numOfPlayerInTheLayers = numOfPlayerInTheLayers;
        this.numLayers = numOfPlayerInTheLayers.length;
    }

    public boolean isValidRow(int playerLocationRow) {
        return (this.numOfPlayerInTheLayers.length > playerLocationRow);

    }

    public boolean isValidColumn(int playerLocationRow, int playerLocationColumn) {
        boolean resp = true;
        if(numOfPlayerInTheLayers[playerLocationRow] > playerLocationColumn) {
            return true;
        }
        else {
            return false;
        }
    }
}
