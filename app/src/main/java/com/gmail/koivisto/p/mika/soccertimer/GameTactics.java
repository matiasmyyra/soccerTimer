package com.gmail.koivisto.p.mika.soccertimer;

public class GameTactics {
    int numLayers;
    int[] numOfPlayerInTheLayers;

    public int[] getNumOfPlayerInTheLayers() {
        return numOfPlayerInTheLayers;
    }

    public int getNumLayers() {
        return numLayers;
    }

    public void setNumLayers(int numLayers) {
        this.numLayers = numLayers;
    }

    public void setNumOfPlayerInTheLayers(int[] numOfPlayerInTheLayers) {
        this.numOfPlayerInTheLayers = numOfPlayerInTheLayers;
    }
}
