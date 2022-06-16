package com.example.navigationview.ui.recyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private RecyclerViewViewModel mViewModel;
    RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    //http
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
       //http
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局
        // recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));//线性宫格显示，类似gridview
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//线性宫格显示类似瀑布流
        adapter=new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void getData() {
        String url=new MyApplication().selectallurl;
        //String url = "http://172.16.26.242:8080/androidweb/ListJsonServlet";
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("RecycleViewFragment", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
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
    class  MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>
    {


        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView picture;
            public TextView brand;
            public TextView earname;
            public TextView sellingtime;
            public TextView price;
            public TextView info;

            public ViewHolder(View convertView) {
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
        public MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.item,parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.ViewHolder holder, final int position) {
            //picture
            Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(position).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(holder.picture);
            holder.brand.setText((String)mData.get(position).get("brand"));
            holder.earname.setText((String)mData.get(position).get("earname"));
            holder.sellingtime.setText((String)mData.get(position).get("sellingtime"));
            holder.price.setText((String)mData.get(position).get("price"));
            holder.info.setText((String)mData.get(position).get("info"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("id",(String)mData.get(position).get("id"));
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
