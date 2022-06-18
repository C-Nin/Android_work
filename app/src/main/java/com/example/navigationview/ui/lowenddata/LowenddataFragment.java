package com.example.navigationview.ui.lowenddata;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;
import com.example.navigationview.ui.highenddata.HighendFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LowenddataFragment extends Fragment {

    private LowenddataViewModel mViewModel;
    RecyclerView recyclerView;
    private LowenddataFragment.MyLowenddataAdapter adapter;

    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();

    public static LowenddataFragment newInstance() {
        return new LowenddataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = view.findViewById(R.id.text_slideshow);

        getData();
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new LowenddataFragment.MyLowenddataAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void getData(){
        String url=new MyApplication().selectlowendurl;
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("SlideshowFragment",result);
                list= JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>(){
                        });
                mData.clear();
                mData.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    class MyLowenddataAdapter extends RecyclerView.Adapter<LowenddataFragment.MyLowenddataAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView picture;
            public TextView brand;
            public TextView earname;
            public TextView sellingtime;
            public TextView price;
            public TextView info;
            public ViewHolder(@NonNull View convertView) {
                super(convertView);
                picture = (ImageView)convertView.findViewById(R.id.picture);
                brand = (TextView)convertView.findViewById(R.id.brand);
                earname = (TextView)convertView.findViewById(R.id.earname);
                sellingtime = (TextView)convertView.findViewById(R.id.sellingtime);
                price = (TextView)convertView.findViewById(R.id.price);
                info = (TextView)convertView.findViewById(R.id.info);
            }
        }
        @NonNull
        @Override
        public LowenddataFragment.MyLowenddataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.item,parent, false);
            return new LowenddataFragment.MyLowenddataAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull LowenddataFragment.MyLowenddataAdapter.ViewHolder holder, final int position) {
            Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(position).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(holder.picture);
            holder.brand.setText((String)mData.get(position).get("brand"));
            holder.earname.setText((String)mData.get(position).get("earname"));
            holder.sellingtime.setText((String)mData.get(position).get("sellingtime"));
            holder.price.setText((String) mData.get(position).get("price"));
            holder.info.setText((String)mData.get(position).get("info"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",(Integer) mData.get(position).get("id"));
                    navController.navigate(R.id.detailFragment,bundle);

                }
            });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
