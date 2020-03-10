package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String tag = "Sensor";
    SensorManager sm = null;
    TextView View1 = null;
    TextView View2 = null;
    TextView View3 = null;
    TextView View4 = null;
    TextView View5 = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        View1 = (TextView) findViewById(R.id.edt1);
        View2 = (TextView) findViewById(R.id.edt2);
        View3 = (TextView) findViewById(R.id.edt3);
        View4 = (TextView) findViewById(R.id.edt4);
        View5 = (TextView) findViewById(R.id.edt5);
        Context mContext = getApplicationContext();
        View1.setText("IP：" + GetLocalIP.getLocalIPAddress(mContext));
    }

    private SensorEventListener listener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                String str = "X：" + event.values[0] + "，\nY：" + event.values[1] + "，\nZ：" + event.values[2];
//                View1.setText("TYPE：" + event.sensor.getType());
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ORIENTATION:       // 方向传感器信息
                        View2.setText("方向传感器：\n" + str);
                        break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /*日志添加信息*/
            Log.d(tag, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        }

    };
    @Override
    protected void onResume() {
        super.onResume();
        /*方向传感器监听注册*/
        sm.registerListener(listener,
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        /*软件关闭时关闭监听传感器信息*/
        sm.unregisterListener(listener);
        super.onStop();
    }

}
