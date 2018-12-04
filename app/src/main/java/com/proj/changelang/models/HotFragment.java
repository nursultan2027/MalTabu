package com.proj.changelang.models;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.activities.FirstSelect1;
import com.proj.changelang.activities.MainActivity2;
import com.proj.changelang.activities.SecondSelect1;
import com.proj.changelang.adapters.HotPostAdapter;
import com.proj.changelang.adapters.HotPostAdapter2;
import com.proj.changelang.adapters.PostAdapter;
import com.proj.changelang.adapters.RecycleHotAdapter;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotFragment extends Fragment {
    private Button select1, select2;
    private JSONArray jsonArray;
    private HotPostAdapter2 adapter;
    private Dialog epicDialog;
    private RecyclerView recyclerView;
    private GridView lst;
    private RecycleHotAdapter myAdapter;
    private FileHelper fileHelper;
    private ArrayList<Post> posts=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
//        select1 = (Button) view.findViewById(R.id.select1);
//        select2 = (Button) view.findViewById(R.id.select2);
        epicDialog = new Dialog(getActivity());
        fileHelper = new FileHelper(getActivity());
        sDialog();
        post();
        Resources resources = getActivity().getResources();
//        if (Maltabu.s2==null)
//        {
//            select1.setText(resources.getString(R.string.Option1));
//        } else
//        {
//            if(Maltabu.s2.length()<11)
//                select1.setText(Maltabu.s2);
//            else {
//                select1.setText(CutString(Maltabu.s2));
//            }
//        }
//
//        if(Maltabu.s4==null)
//        {
//            select2.setText(resources.getString(R.string.Option2));
//        }else
//        {
//            if(Maltabu.s4.length()<11)
//                select2.setText(Maltabu.s4);
//            else {
//                select2.setText(CutString(Maltabu.s4));
//            }
//        }
//        select1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), FirstSelect1.class));
//                getActivity().finish();
//            }
//        });
//        select2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), SecondSelect1.class));
//                getActivity().finish();
//            }
//        });
        lst = (GridView) view.findViewById(R.id.hots);
//        ListView asd = (ListView) view.findViewById(R.id.hots);
        adapter = new HotPostAdapter2(getActivity(),posts);
        lst.setAdapter(adapter);
//        asd.setAdapter(adapter);

        return view;
    }

    private String CutString(String str){
        return str.substring(0, 11)+"...";
    }
    private void post() {

        String url = "http://maltabu.kz/v1/api/clients/data/hot";
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url(url)
                .get()
                .addHeader("isAuthorized", "false")
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    try {
                        jsonArray = new JSONArray(s1);
                        catalogList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }


    private void catalogList() throws JSONException {
        Gson googleJson = new Gson();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(jsonArray), ArrayList.class);
        for (int i = 0; i < postObjList.size(); i++) {
            JSONObject postObject = jsonArray.getJSONObject(i);
            JSONArray arr = postObject.getJSONArray("images");
            ArrayList<Image> imagesArrayList = new ArrayList<>();
            ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
            for (int j = 0; j < imgObjList.size(); j++) {
                JSONObject imgJson = arr.getJSONObject(j);
                Image image = new Image(imgJson.getString("small"));
                imagesArrayList.add(image);
            }
            String cityID = postObject.getJSONObject("cityID").getString("name");
            int number = postObject.getInt("number");
            String price = postObject.getJSONObject("price").getString("kind");
            if (price.equals("value")) {
                price = String.valueOf(postObject.getJSONObject("price").getInt("value"));
            } else {
                if (price.equals("trade")) {
                    if (Maltabu.lang.toLowerCase().equals("ru")) {
                        price = "Договорная цена";
                    } else {
                        String kazName = fileHelper.diction().getString("Договорная цена");
                        price = kazName;
                    }
                } else {
                    if (price.equals("free")) {
                        if (Maltabu.lang.toLowerCase().equals("ru")) {
                            price = "Отдам даром";
                        } else {
                            String kazName = fileHelper.diction().getString("Отдам даром");
                            price = kazName;
                        }
                    }
                }
            }
            Post post = new Post(cityID, price, String.valueOf(number), imagesArrayList);
            posts.add(post);
        }
        adapter.notifyDataSetChanged();getActivity().setTitle(String.valueOf(posts.size()));

        epicDialog.dismiss();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }



}
