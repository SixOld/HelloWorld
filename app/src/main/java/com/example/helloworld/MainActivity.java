package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView View6 = null;
    TextView View7 = null;
    TextView View8 = null;
    TextView View9 = null;
    TextView View10 = null;
    TextView View11 = null;
    TextView View12 = null;
    float[] gravity = new float[3];//用来保存加速度传感器的值
    float[] r = new float[9];//
    float[] geomagnetic = new float[3];//用来保存地磁传感器的值
    float[] values = new float[3];//用来保存最终的结果

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
        View6 = (TextView) findViewById(R.id.edt6);
        View7 = (TextView) findViewById(R.id.edt7);
        View8 = (TextView) findViewById(R.id.edt8);
        View9 = (TextView) findViewById(R.id.edt9);
        View10 = (TextView) findViewById(R.id.edt10);
        View11 = (TextView) findViewById(R.id.edt11);
        View12 = (TextView) findViewById(R.id.edt12);
    }
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                String str = "X：" + event.values[0] + "，Y：" + event.values[1] + "，Z：" + event.values[2];
                View1.setText("TYPE：" + event.sensor.getType());
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        View2.setText("加速度：" + str);
                        gravity = event.values;
                        getOritation();
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        View3.setText("陀螺仪：" + str);
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        View4.setText("磁力计：" + str);
                        geomagnetic = event.values;
                        break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(tag, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        }

        /**
         * 获取手机旋转角度
         */
        public void getOritation() {
            // r从这里返回
            SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
            //values从这里返回
            SensorManager.getOrientation(r, values);
            //提取数据
            double degreeZ = Math.toDegrees(values[0]);
            double degreeX = Math.toDegrees(values[1]);
            double degreeY = Math.toDegrees(values[2]);
            View5.setText("X：" + degreeX);
            View6.setText("Y：" + degreeY);
            View7.setText("Z：" + degreeZ);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(listener,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(listener,
                sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(listener,
                sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(listener);
        super.onStop();
    }

}
