package com.example.navigationview.ui.card;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;
import com.example.navigationview.mqtt.MqttManager;
import com.lichfaker.log.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardFragment extends Fragment {

    private CardViewModel mViewModel;
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();
    EditText myrfid;
    Button read;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment, container, false);
        myrfid = view.findViewById(R.id.editText);
        read=view.findViewById(R.id.button);

        //接收HomeFragment传来的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        //获取宠物编号
                        String tvcwbh=getArguments().getString("mesg");
                        myrfid.setText(tvcwbh);

                        //http:
//                        String url=new MyApplication().rfidselect;
//                        RequestParams params = new RequestParams(url);
//                        //get
//                        params.addQueryStringParameter("rfid", tvcwbh);
//
//                        x.http().get(params, new Callback.CommonCallback<String>() {
//                            @Override
//                            public void onSuccess(String result) {
//                                //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                                list = JSON.parseObject(result,
//                                        new TypeReference<List<HashMap<String, Object>>>() {
//                                        });
//                                mData.addAll(list);
//
//                                ImageView picture = view.findViewById(R.id.xscwzp);
//                                //showpicture
//                                xscwbh.setText(mData.get(0).get("宠物编号").toString());
//
//                            }
//
//                            @Override
//                            public void onError(Throwable ex, boolean isOnCallback) {
//                                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onCancelled(Callback.CancelledException cex) {
//                                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onFinished() {
//
//                            }
//                        });


                    }
                });

            }
        }).start();


        return view;
    }

}
