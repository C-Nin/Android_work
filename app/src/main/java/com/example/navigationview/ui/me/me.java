package com.example.navigationview.ui.me;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;
import com.example.navigationview.mqtt.MqttManager;
import com.example.navigationview.ui.collection.CollectionFragment;
import com.example.navigationview.ui.viewpaper.ViewPaperViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lichfaker.log.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class me extends Fragment {

    private MeViewModel mViewModel;
    public static final String rfidurl = "tcp://120.79.130.114:1883";
    //public static final String URL = "tcp://broker.hivemq.com:1883";
    private String rfiduserName = "text";
    private String rfidpassword = "text123";
    private String rfidclientId = "1940707217";
    private String mesg;

    public static me newInstance() {
        return new me();
    }
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();
    private ViewPaperViewModel viewPaperViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewPaperViewModel =
                ViewModelProviders.of(this).get(ViewPaperViewModel.class);
        final View root = inflater.inflate(R.layout.me_fragment, container, false);
        final EditText urfid=root.findViewById(R.id.editText2);

//        Button exit=getActivity().findViewById(R.id.button);
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        TextView crad=root.findViewById(R.id.textView10);
        crad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
                String url=new MyApplication().rfidselect;
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
                        Log.d("a", String.valueOf(mData));
                        //showpicture
                        int i =mData.size();
                        urfid.setText(mData.get(i-1).get("rfid").toString());
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
            }
        });

        Button exit=root.findViewById(R.id.buttons);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.login);


            }
        });

        return root;
    }

}

