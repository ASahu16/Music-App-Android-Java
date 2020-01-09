package com.example.yuvi.music_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    ImageView img;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TextView zoom=(TextView)findViewById(R.id.txt);

        final Animation zoomAnimation= AnimationUtils.loadAnimation(this,R.anim.zooom);
        zoom.startAnimation(zoomAnimation);

        img=(ImageView) findViewById(R.id.music);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,MainActivity.class));

            }
        });
    }
}
