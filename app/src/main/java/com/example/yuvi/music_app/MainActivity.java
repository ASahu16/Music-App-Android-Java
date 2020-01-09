package com.example.yuvi.music_app;

import android.content.Intent;
import android.hardware.*;
import android.hardware.Sensor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
 ListView lv;
    String[] items;
    TextView txt;
    Integer sensevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv);

        final ArrayList<File>mySongs=findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for (int i=0;i<mySongs.size();i++){
            items[i]=mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");

        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",i).putExtra("songlist",mySongs));

            }
        });
    }
    public ArrayList<File>findSongs(File root){

        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findSongs(singleFile));

            }
            else {
                if(singleFile.getName().endsWith(".mp3")||singleFile.getName().endsWith(".wav")) {
                    al.add(singleFile);

                }

            }

        }
        return al;
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        String.valueOf(sensorEvent.values[0]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
