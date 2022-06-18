package com.example.navigationview.ui.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class DetailFragment extends Fragment {

    private DetailViewModel mViewModel;
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();
    int id;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_detail, container, false);
        id=getArguments().getInt("id");
        String url=new MyApplication().selectbyidurl;
        // String url = "http://172.16.26.242:8080/androidweb/SelectServlet";
        RequestParams params = new RequestParams(url);
        //get
        params.addParameter("id", id);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.addAll(list);

                ImageView picture = (ImageView)view.findViewById(R.id.picture);
                //showpicture
                Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(0).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);

                collapsingToolbar.setTitle(mData.get(0).get("brand").toString());

                TextView tvearname=(TextView)view.findViewById(R.id.earname);
                tvearname.setText(mData.get(0).get("earname").toString());

                TextView tvprice=(TextView)view.findViewById(R.id.price);
                tvprice.setText(mData.get(0).get("price").toString());

                TextView tvinfo=(TextView)view.findViewById(R.id.info);
                tvinfo.setText(mData.get(0).get("info").toString());

                TextView tvsellingtime= view.findViewById(R.id.sellingtime);
                tvsellingtime.setText(mData.get(0).get("sellingtime").toString());
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
        Button del_book= (Button) view.findViewById(R.id.del_book);
        del_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=new MyApplication().deletebyidurl;
                // String url = "http://172.16.26.242:8080/androidweb/DeleteServlet";
                RequestParams params = new RequestParams(url);
                //get
                params.addParameter("id", id);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getActivity(), "删除数据成功！", Toast.LENGTH_LONG).show();
                        NavController navController= Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_home);
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
        Button edit_book= (Button) view.findViewById(R.id.edit_book);
        edit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                navController.navigate(R.id.nav_editbook,bundle);
            }
        });
        return  view;
    }
}
