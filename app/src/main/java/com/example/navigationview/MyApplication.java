package com.example.navigationview;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {

    public String selectallurl="http://172.17.141.43/json2/listjson.php";
    public String inserturl="http://172.17.141.43/json2/insert.php";
    public String selectbyidurl="http://172.17.141.43/json2/select.php";
    public String deletebyidurl="http://172.17.141.43/json2/delete.php";
    public String updatebyidurl="http://172.17.141.43/json2/update.php";
    public String imagebaseurl="http://172.17.141.43/json2/images/";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}