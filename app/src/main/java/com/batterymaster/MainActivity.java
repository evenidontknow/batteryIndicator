package com.batterymaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mBatteryLevelText,batterylevel;
    private ProgressBar mBatteryLevelProgress;
    private BroadcastReceiver mReceiver;
    private Button enable,stop,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBatteryLevelText = (TextView) findViewById(R.id.textView);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);
        mReceiver = new BatteryBroadcastReceiver();
        status = (Button) findViewById(R.id.status);
        batterylevel= (TextView) findViewById(R.id.textView2);
        enable = (Button) findViewById (R.id.ebutton);
        stop = (Button) findViewById (R.id.sbutton);



    }

    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();


    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }



    public class BatteryBroadcastReceiver extends BroadcastReceiver {
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        public final static String BATTERY_LEVEL = "level";

        @Override
        public void onReceive(Context context, Intent intent) {
            final int level = intent.getIntExtra(BATTERY_LEVEL, 0);

            mBatteryLevelText.setText("Battery Level: " + " " + level);
            mBatteryLevelProgress.setProgress(level);
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(level<=20)
                    {
                        batterylevel.setText("Battery Status:Low");

                    }
                    else
                    {
                        batterylevel.setText("Battery Status:Good");

                    }

                }
            });

            enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Full Charge Alaram set",Toast.LENGTH_SHORT).show();
                    if(level == 20



                            ) {
                        r.play();


                    }

                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.stop();
                    Toast.makeText(getApplicationContext(),"Alaram stopped",Toast.LENGTH_SHORT).show();
                }
            });



        }
    }



}
