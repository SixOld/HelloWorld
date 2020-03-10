package com.example.helloworld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class GetLocalIP {
    /*获取局域网 IP 地址*/
    public static String getLocalIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
            return ipAddress;
        }
        return "";
    }

    /*整形 IP 转换成字符串 IP*/
    private static String intIP2StringIP(int ipInt) {
        String sb = (ipInt & 0xFF) + "." +
                ((ipInt >> 8) & 0xFF) + "." +
                ((ipInt >> 16) & 0xFF) + "." +
                ((ipInt >> 24) & 0xFF);
        return sb;
    }

}
