package com.example.yuvi.music_app;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yuvi on 02-03-2017.
 */
public class Sensor extends Activity {
    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private PowerManager.WakeLock mWakeLock;
    private Button button;
    private TextView textView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            // Get an instance of the SensorManager
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            // Get an instance of the PowerManager
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

            // Get an instance of the WindowManager
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.getDefaultDisplay();

            // Create a bright wake lock
            mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                    .getName());

            setContentView(R.layout.main);
            textView = (TextView)findViewById(R.id.text1);
            button = (Button)findViewById(R.id.image1);
            button.setOnClickListener(mButtonStopListener);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onCreate", e.getMessage());
        }
    } // END onCreate

    View.OnClickListener mButtonStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                mWakeLock.release();
                textView.setText("mWakeLock.release()");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.e("onPause",e.getMessage());
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
	        /*
	         * when the activity is resumed, we acquire a wake-lock so that the
	         * screen stays on, since the user will likely not be fiddling with the
	         * screen or buttons.
	         */

        try {
            mWakeLock.acquire();
            textView.setText("mWakeLock.acquire()");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onResume", e.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // and release our wake-lock
        try {
            mWakeLock.release();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("onPause",e.getMessage());
        }
    }
}
