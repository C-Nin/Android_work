package com.example.navigationview;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {

//    public String selectallurl="http://172.17.141.43/json2/listjson.php";
//    public String inserturl="http://172.17.141.43/json2/insert.php";
//    public String selectbyidurl="http://172.17.141.43/json2/select.php";
//    public String deletebyidurl="http://172.17.141.43/json2/delete.php";
//    public String updatebyidurl="http://172.17.141.43/json2/update.php";
//    public String imagebaseurl="http://172.17.141.43/json2/images/";

//    public String selectallurl="http://172.17.141.43:8000/select/";
////    public String inserturl="http://172.17.141.43:8000/add/";
////    public String selectbyidurl="http://172.17.141.43:8000/select2/";
////    public String deletebyidurl="http://172.17.141.43:8000/delete/";
////    public String updatebyidurl="http://172.17.141.43:8000/update/";
////    public String imagebaseurl="http://172.17.141.43/android/image2/";
////    public String selectmiurl="http://172.17.141.43:8000/mi/";
////    public String selecthuaweiurl="http://172.17.141.43:8000/HUAWEI/";
////    public String selecedifierturl="http://172.17.141.43:8000/EDIFIER/";
////    public String selecthighendurl="http://172.17.141.43:8000/highend/";
////    public String selectlowendurl="http://172.17.141.43:8000/lowend/";
////    public String selectCollectionurl="http://172.17.141.43:8000/Collectionselect/";
////    public String selectCollectionbyidurl="http://172.17.141.43:8000/Collectionselect2/";
////    public String Collectionaddurl="http://172.17.141.43:8000/Collectionadd/";
////    public String Collectiondeleteurl="http://172.17.141.43:8000/Collectiondelete/";

    public String selectallurl="http://172.17.142.164:8000/select/";
    public String inserturl="http://172.17.142.164:8000/add/";
    public String selectbyidurl="http://172.17.142.164:8000/select2/";
    public String deletebyidurl="http://172.17.142.164:8000/delete/";
    public String updatebyidurl="http://172.17.142.164:8000/update/";
    public String imagebaseurl="http://172.17.142.164/android/image2/";
    public String selectmiurl="http://172.17.142.164:8000/mi/";
    public String selecthuaweiurl="http://172.17.142.164:8000/HUAWEI/";
    public String selecedifierturl="http://172.17.142.164:8000/EDIFIER/";
    public String selecthighendurl="http://172.17.142.164:8000/highend/";
    public String selectlowendurl="http://172.17.142.164:8000/lowend/";
    public String selectCollectionurl="http://172.17.142.164:8000/Collectionselect/";
    public String selectCollectionbyidurl="http://172.17.142.164:8000/Collectionselect2/";
    public String Collectionaddurl="http://172.17.142.164:8000/Collectionadd/";
    public String Collectiondeleteurl="http://172.17.142.164:8000/Collectiondelete/";
    public String rfidselect="http://172.17.142.164:8000/rfidselect/";
    public String rfid2select="http://172.17.142.164:8000/rfid2select/";
    public String rfid2add="http://172.17.142.164:8000/rfid2add/";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // ????????????debug??????, ??????debug???????????????.
    }
}