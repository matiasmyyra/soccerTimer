package com.gmail.koivisto.p.mika.soccertimer;

import android.util.Log;

class MyLog {
    public static void e(String tag, String s) {
        Common c = new Common();
        if(c.isRunningTest() == false){
            Log.e(tag,s);
        }

    }
}
