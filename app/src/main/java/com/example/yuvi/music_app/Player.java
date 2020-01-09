package com.example.yuvi.music_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.hardware.*;
import android.hardware.Sensor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class Player extends AppCompatActivity implements View.OnClickListener,SensorEventListener {
    static MediaPlayer mp;
    ArrayList<File> mySongs;
    SeekBar sb;
    int position;
    Button btn;
    Uri u;
    Thread th;
    Button btPlay, btFb, btff, btpv, btnxt;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    Integer senseresult;
    private SensorManager sm;
     private Sensor sensor;
    TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        display=(TextView)findViewById(R.id.display);

        btPlay = (Button) findViewById(R.id.btnPlay);
        btff = (Button) findViewById(R.id.btnff);
        btFb = (Button) findViewById(R.id.btnFB);
        btpv = (Button) findViewById(R.id.btPy);
        btnxt = (Button) findViewById(R.id.btnnxt);
        btn = (Button)findViewById(R.id.backbtn);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        btPlay.setOnClickListener(this);
        btff.setOnClickListener(this);
        btFb.setOnClickListener(this);
        btpv.setOnClickListener(this);
        btnxt.setOnClickListener(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Player.this,MainActivity.class));
            }
        });



        sb = (SeekBar) findViewById(R.id.sb);

        th = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
                sb.setMax(totalDuration);
                while (mp!=null && currentPosition<totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mp.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    }
                    catch (Exception otherException){
                        return;
                    }
                    sb.setProgress(currentPosition);


                }
                //super.run();
            }
        };


        if (mp != null) {
            mp.start();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos", 0);
        u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        sb.setMax(mp.getDuration());

        th.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());

            }

        });


    }


    @Override
    public void onClick(View view) {
        try {

            int id = view.getId();
            switch (id) {
                case R.id.btnPlay:
                    if (mp.isPlaying()) {
                        btPlay.setText(">");
                        mp.pause();
                    } else {
                        mp.start();
                        btPlay.setText("||");
                    }
                    break;
                case R.id.btnff:
                    mp.seekTo(mp.getCurrentPosition() + 5000);
                    break;
                case R.id.btnFB:
                    mp.seekTo(mp.getCurrentPosition() - 5000);
                    break;
                case R.id.btnnxt:
                    mp.stop();
                    mp.release();
                    position = (position + 1) % mySongs.size();
                    u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(), u);
                    mp.start();
                    sb.setMax(mp.getDuration());
                    break;
                case R.id.btPy:
                    mp.stop();
                    mp.release();
                    position = (position - 1 < 0) ? mySongs.size() - 1 : position - 1;
               /* if(position-1<0){
                    position = mySongs.size()-1;
                }
                else {
                    position = position-1;
                }*/
                    u = Uri.parse(mySongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(), u);
                    mp.start();
                    sb.setMax(mp.getDuration());
                    break;


            }
        }
        catch (Exception e){
            Log.e("Player::MyMethod",e.getMessage());
        }
        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                } else {
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });

        /**
         * Button Click event for Shuffle button
         * Enables shuffle flag to true
         * */
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        try {


            display.setText("X" + sensorEvent.values[0] + "\nY" + sensorEvent.values[1] + "\nZ" + sensorEvent.values[2]);
            if (sensorEvent.values[0] > 10) {
                mp.stop();
                mp.release();
                position = (position + 1) % mySongs.size();
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                sb.setMax(mp.getDuration());



            }



        }
        catch (Exception e){
            Log.e("PLayer::MyMethod",e.getMessage());
        }

        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
