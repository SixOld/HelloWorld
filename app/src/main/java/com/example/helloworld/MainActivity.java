package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    final String tag = "Sensor";
    SensorManager sm = null;
    TextView View1 = null;
    TextView View2 = null;
    TextView View3 = null;
    TextView View4 = null;
    TextView View5 = null;
    private TextView tv_content, tv_send_text;
    private Button bt_send;
    private Button bt_clean;
    String ShowInfomation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*输出框初始化*/
        View1 = (TextView) findViewById(R.id.edt1);
        View2 = (TextView) findViewById(R.id.edt2);
        View3 = (TextView) findViewById(R.id.edt3);
        View4 = (TextView) findViewById(R.id.edt4);
        View5 = (TextView) findViewById(R.id.edt5);
        /*获取IP相关*/
        Context mContext = getApplicationContext();
        View1.setText("IP：" + GetLocalIP.getLocalIPAddress(mContext));
        /*网络连接相关*/
        InitView();
        MobileServer mobileServer = new MobileServer();
        mobileServer.setHandler(handler);
        new Thread(mobileServer).start();
    }

    private void InitView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_send_text = (TextView) findViewById(R.id.tv_send_text);
        bt_send = (Button) findViewById(R.id.bt_send);
        bt_clean = (Button) findViewById(R.id.bt_clean);
        bt_send.setOnClickListener(clickListener);
        bt_clean.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_send:
                    char[] test = new char[3];
                    test[0] = 15;
                    test[1] = 16;
                    test[2] = 17;
                    String str = String.valueOf(test);
                    new SendAsyncTask().execute(str);
                    tv_send_text.setText(str);
                    break;
                case R.id.bt_clean:
                    ShowInfomation = "";
                    tv_content.setText("WiFi模块发送的：\n" + ShowInfomation);
            }
        }
    };

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                String str = "X：" + event.values[0] + "，\nY：" + event.values[1] + "，\nZ：" + event.values[2];
                if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {       // 方向传感器信息
                    View3.setText("方向传感器：\n" + str);
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
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ShowInfomation += msg.obj;
                    tv_content.setText("WiFi模块发送的：\n" + ShowInfomation);
                    Toast.makeText(MainActivity.this, "接收到信息", Toast.LENGTH_LONG)
                            .show();
            }
        }
    };

}
