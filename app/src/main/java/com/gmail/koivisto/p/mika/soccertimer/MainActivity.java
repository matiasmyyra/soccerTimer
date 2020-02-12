package com.gmail.koivisto.p.mika.soccertimer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    Button timerGetButton;
    GameTime gameTime;
    GameData gameData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        gameTime = new GameTime();
        gameData = new GameData();
        timerGetButton = findViewById(R.id.buttonGetTime);
        initData();
        addTimeToTextView();

    }
        public void onClickTimeButton(View v) {
            if (v.getId() == R.id.buttonGetTime) {
                addTimeToTextView();
            } else {
                throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }

    private void addTimeToTextView() {
        String TAG = "addTimeToTextView";
        Log.i(TAG, "Clicked!!");

        String formattedDate = gameTime.getCurrentTimeAndDateStr();

        // Now we display formattedDate value in TextView
        setContentView(R.layout.activity_main);

        TextView newtext = findViewById(R.id.currentTme);
        String text;
        text = getResources().getString(R.string.CurentDateAndTimeText) + formattedDate;

        newtext.setText(text);
    }
    private void initData() {
        String TAG = "initData";
        Log.i(TAG, "init start");
        gameData.setGameStartTime(gameTime.getCurrentTimeAndDate());
        gameData.setGameMode(GameMode.GAME_MODE_8VS8);
        int goalkeeper = 1;
        int[] tactic ={1,3,3,goalkeeper};
        gameData.gameTactics.setNumLayers(tactic.length);
        gameData.gameTactics.setNumOfPlayerInTheLayers(tactic);

        String[] palayersName  = {"Kallela Anna",
        "Kilpelänaho Maria",
        "Leppälä Iina",
        "Nieminen Jemina",
        "Niiranen Ella",
        "Nylund Laura",
        "Nylund Emilia",
        "Pehkonen Minnea",
        "Äijälä Iina-Aurora",
        "Karekivi Meriina",
        "Mustonen  Mira",
        "Määttä Eetla",
        "Palosaari Helinä",
        "Kaurala Silja",
        "Brusi Tia",
        "Alanen Helmi",
        "Poukkula Martta",
        "Alila Emilia",
        "Kallela Sofia",
        "Kolehmainen Maija",
        "Koivisto Amanda",
        "Ervasti Emilia",
        "Niemelä Iina",
        "Valkonen Maini",
        "Krasniqi Rina",
        "Lastikka Heidi"};

        gameData.setPlayerNames(palayersName);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
