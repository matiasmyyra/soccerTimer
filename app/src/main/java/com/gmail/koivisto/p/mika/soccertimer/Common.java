package com.gmail.koivisto.p.mika.soccertimer;

public class Common {
    private Boolean isRunningTest = null;
    public boolean isRunningTest() {
        if (isRunningTest == null) {
            isRunningTest = true;
            try {
                Class.forName("org.junit.Test");
            } catch (ClassNotFoundException e) {
                isRunningTest = false;
            }
        }
        return isRunningTest;
    }
}
