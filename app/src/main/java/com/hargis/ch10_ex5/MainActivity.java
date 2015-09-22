package com.hargis.ch10_ex5;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener   {

    private TextView messageTextView; 
    private Button startButton;
    private Button stopButton;
    private boolean stopwatchOn;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        startTimer();

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        Log.d("Main Activity", "App Started");

        // Start service

    }



    public void onPause(){

        super.onPause();



    }
    @Override
    public void onResume(){
        super.onResume();

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopButton:

                    stopTimer();

                break;
            case R.id.startButton:
                startTimer();
                break;
        }
    }



    public IBinder onBind(Intent intent) {
        Log.d("Main Activity", " No binding");
        return null;
    }

    @Override
    public void onDestroy(){

        Log.d("Main Activity", " Service Destroyed");
        stopTimer();
    }

    private void startTimer() {
        final long startMillis = System.currentTimeMillis();

        TimerTask task = new TimerTask() {
            
            @Override
            public void run() {
                Log.d("main activity", "timer task has executed ");
                long elapsedMillis = System.currentTimeMillis() - startMillis;
                updateView(elapsedMillis);
            }
        };
        Timer timer = new Timer(true);
        int delay = 1000 * 10; // 10 seconds
        int interval = 1000 * 10;

        timer.schedule(task, delay, interval);
    }


    private void stopTimer() {
        if(timer != null){
            timer.cancel();
        }
    }

    private void updateView(final long elapsedMillis) {
        // UI changes need to be run on the UI thread
        messageTextView.post(new Runnable() {

            int elapsedSeconds = (int) elapsedMillis / 1000;

            @Override
            public void run() {
                messageTextView.setText("Seconds " + elapsedSeconds);
            }
        });
    }


}