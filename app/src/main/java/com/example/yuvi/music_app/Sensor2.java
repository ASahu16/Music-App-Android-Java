package com.example.yuvi.music_app;

import android.app.Activity;
import android.hardware.*;
import android.hardware.Sensor;
import android.media.MediaPlayer;

/**
 * Created by yuvi on 09-03-2017.
 */
public class Sensor2 extends Activity implements SensorEventListener {
    MediaPlayer mp3;
    SensorManager sm;
    Player ply;
    Float x,y,senorX,sensorY;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
