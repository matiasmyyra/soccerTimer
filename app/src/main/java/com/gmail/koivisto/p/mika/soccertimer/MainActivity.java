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


public class MainActivity extends AppCompatActivity {
    Button timerGetButton;
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

        timerGetButton = (Button) findViewById(R.id.buttonGetTime);
        String TAG = "onCreate";
        Log.i(TAG, "Start!!");
        String formattedDate = getCurrentTimeAndDate();
        // Now we display formattedDate value in TextView
        setContentView(R.layout.activity_main);

        TextView newtext = (TextView) findViewById(R.id.currentTme);

        newtext.setText("Current Date and Time : "+formattedDate);

    }
        public void onClickTimeButton(View v) {
            switch(v.getId()) {
                case R.id.buttonGetTime:
                    String TAG = "button clik";
                    Log.i(TAG, "Clicked!!");
                    String formattedDate = getCurrentTimeAndDate();
                    // Now we display formattedDate value in TextView
                    setContentView(R.layout.activity_main);

                    TextView newtext = (TextView) findViewById(R.id.currentTme);

                    newtext.setText("Current Date and Time : "+formattedDate);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }

        public String getCurrentTimeAndDate() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());

        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

        return formattedDate;
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
