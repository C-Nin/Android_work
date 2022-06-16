package com.example.navigationview.ui.library;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.navigationview.MyApplication;
import com.example.navigationview.R;
import com.linchaolong.android.imagepicker.ImagePicker;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class EditBookFragment extends Fragment {
    // 自动完成文本框
    AutoCompleteTextView brand;
    String[] brands = { "华为", "小米", "AIRPODS" };

    // 退出按钮
    static final int EXIT_DIALOG_ID = 0;
    // 出售日期
    EditText sellingtime;
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    //picture
    private ImagePicker imagePicker = new ImagePicker();
    ImageView picture;
    //save
    EditText earname;
    EditText price;
    EditText info;
    //http
    String id;
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    private List<HashMap<String,Object>> mData=new ArrayList<HashMap<String,Object>>();
    String deletefileurl;
    String fileurl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_editbook, container, false);
        id=getArguments().getString("id");
        // 自动完成文本框
        brand = (AutoCompleteTextView) view.findViewById(R.id.brand);
        ArrayAdapter<String> adapterauto = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, brands);
        brand.setAdapter(adapterauto);

        // 退出按钮
        Button exitButton = (Button) view.findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onCreateDialog(EXIT_DIALOG_ID);

            }
        });
        // 出版日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        sellingtime = (EditText) view.findViewById(R.id.sellingtime);
        sellingtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onCreateDialog(DATE_DIALOG_ID);
            }
        });

        // picture功能
        picture = (ImageView) view.findViewById(R.id.photo);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraOrGallery();
            }
        });

        //save(brand,sellingtime,picture已有)
        earname = (EditText) view.findViewById(R.id.earname);
        price = (EditText) view.findViewById(R.id.price);
        info = (EditText) view.findViewById(R.id.info);
        //显示数据
        showdata();

        Button save= (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txtbrand = brand.getText().toString();
                final String txtearname = earname.getText().toString();
                final String txtsellingtime = sellingtime.getText().toString();
                final String txtprice = price.getText().toString();
                final String txtinfo = info.getText().toString();
                String url=new MyApplication().updatebyidurl;
                //String url="http://172.16.26.242:8080/androidweb/UpdateServlet";
                RequestParams params = new RequestParams(url);
                //post
                params.setMultipart(true);
                params.addBodyParameter("id", id);
                params.addBodyParameter("brand", txtbrand);
                params.addBodyParameter("earname", txtearname);
                params.addBodyParameter("sellingtime", txtsellingtime);
                params.addBodyParameter("price", txtprice);
                params.addBodyParameter("info", txtinfo);
                params.addBodyParameter("uploadedfile", new File(fileurl));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
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

                NavController navController= Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_home);

            }
        });
        return view;
    }

    private void showdata() {
        String url=new MyApplication().selectbyidurl;
        //String url = "http://172.16.26.242:8080/androidweb/SelectServlet";
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("id", id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.addAll(list);
               //showpicture
                Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(0).get("picture").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                brand.setText(mData.get(0).get("brand").toString());
                earname.setText(mData.get(0).get("earname").toString());
                sellingtime.setText(mData.get(0).get("sellingtime").toString());
                price.setText(mData.get(0).get("price").toString());
                info.setText(mData.get(0).get("info").toString());
                deletefileurl=mData.get(0).get("picture").toString();
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

    // 对话框创建
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case EXIT_DIALOG_ID:// 退出对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setIcon(R.drawable.ic_menu_gallery);
                builder.setTitle("你确定要离开吗？");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击确定后的逻辑
                                getActivity().finish();
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击取消后的逻辑
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case DATE_DIALOG_ID:// 出版日期对话框
                new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth,
                        mDay).show();
                break;

        }
        return null;
    }

    // 出版日期
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    // 出版日期 updates the date in the TextView
    private void updateDisplay() {
        sellingtime.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mYear).append("-").append(mMonth + 1).append("-")
                .append(mDay));
    }
    //picture
    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    private void startCameraOrGallery() {
        new AlertDialog.Builder(getActivity()).setTitle("设置图片")
                .setItems(new String[] { "从相册中选取图片", "拍照" }, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override public void onPickImage(Uri imageUri) {
                            }

                            @Override public void onCropImage(Uri imageUri) {
                                //picture.setImageURI(imageUri);
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(picture);
                                fileurl=imageUri.getPath();
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(EditBookFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(EditBookFragment.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }
}
