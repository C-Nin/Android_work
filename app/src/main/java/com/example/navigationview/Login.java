package com.example.navigationview;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.navigationview.mqtt.MqttManager;
import com.example.navigationview.ui.card.CardFragment;
import com.lichfaker.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class Login extends AppCompatActivity implements View.OnClickListener{
    private DBOpenHelper mDBOpenHelper;
    private EditText et_User, et_Psw;
    private CheckBox cb_rmbPsw;
    private String userName;
    private SharedPreferences.Editor editor;
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();
    public static final String rfidurl = "tcp://120.79.130.114:1883";
    //public static final String URL = "tcp://broker.hivemq.com:1883";
    private String rfiduserName = "text";
    private String rfidpassword = "text123";
    private String rfidclientId = "1940707217";
    private String mesg;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        Objects.requireNonNull(getSupportActionBar()).hide();//隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止横屏
        setContentView(R.layout.activity_login);

        initView();//初始化界面
        mDBOpenHelper = new DBOpenHelper(this);

        SharedPreferences sp = getSharedPreferences("user_mes", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getBoolean("flag",false)){
            String user_read = sp.getString("user","");
            String psw_read = sp.getString("psw","");
            et_User.setText(user_read);
            et_Psw.setText(psw_read);
        }

    }

    private void initView() {
        //初始化控件
        et_User = findViewById(R.id.et_User);
        et_Psw = findViewById(R.id.et_Psw);
        cb_rmbPsw = findViewById(R.id.cb_rmbPsw);
        Button btn_Login = findViewById(R.id.btn_Login);
        Button btn_Login2 = findViewById(R.id.button2);
        TextView tv_register = findViewById(R.id.tv_Register);
        //设置点击事件监听器
        btn_Login.setOnClickListener( this);
        btn_Login2.setOnClickListener( this);
        tv_register.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_Register: //注册
                Intent intent = new Intent(Login.this, RegisteredActivity.class);//跳转到注册界面
                startActivity(intent);
                finish();
                break;

            case R.id.btn_Login:
                String name = et_User.getText().toString().trim();
                String password = et_Psw.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    boolean match2 = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if ((name.equals(user.getName()) && password.equals(user.getPassword()))||
                                (name.equals(user.getEmail())&&password.equals(user.getPassword()))||
                                (name.equals(user.getPhonenum())&&password.equals(user.getPassword()))) {
                            userName = user.getName();
                            match = true;
                            if(cb_rmbPsw.isChecked()){
                                editor.putBoolean("flag",true);
                                editor.putString("user",user.getName());
                                editor.putString("psw",user.getPassword());
                                editor.apply();
                                match2 = true;
                            }else {
                                editor.putString("user",user.getName());
                                editor.putString("psw","");
//                                editor.clear();
                                editor.apply();
                                match2 = false;
                            }
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        if(match2){
                            Toast.makeText(this, "成功记住密码", Toast.LENGTH_SHORT).show();
                            cb_rmbPsw.setChecked(true);
                        }
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Runnable target;
                        //用线程启动
                        Thread thread = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    sleep(2000);//2秒 模拟登录时间
                                    String user_name = userName;
                                    Intent intent1 = new Intent(Login.this, MainActivity.class);//设置自己跳转到成功的界面

                                    //intent1.putExtra("user_name",user_name);
                                    startActivity(intent1);
                                    finish();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();//打开线程
                    } else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button2:
                //code
                String url=new MyApplication().rfid2select;
                // String url = "http://172.16.26.242:8080/androidweb/DeleteServlet";
                RequestParams params = new RequestParams(url);
                //get
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        list = JSON.parseObject(result,
                                new TypeReference<List<HashMap<String, Object>>>() {
                                });
                        mData.addAll(list);
                        Log.d("aa", String.valueOf(mData));
                        int i=mData.size();
                        if(mData==null){
                            Toast.makeText(x.app(), "卡号不存在", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(x.app(), "登录成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);//跳转到注册界面
                            startActivity(intent);
                            finish();

                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFinished() {

                    }
                });
                break;
        }
    }


}
