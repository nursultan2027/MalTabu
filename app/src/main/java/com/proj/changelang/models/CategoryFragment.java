package com.proj.changelang.models;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.adapters.PostAdapter;
import com.proj.changelang.adapters.PostRecycleAdapter;
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


public class CategoryFragment extends Fragment {
    private JSONArray resObj;
    private String catalog;
    private int page;
    private ArrayList<Post> posts;
    private boolean isCatalog;
    private ListView lst;
    private Dialog epicDialog;
    private ProgressBar button;
    private boolean can = true;
    private FileHelper fileHelper;
    private PostAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        posts = new ArrayList<>();
        Bundle bundle = this.getArguments();
        epicDialog = new Dialog(getActivity());
        fileHelper = new FileHelper(getActivity());
        isCatalog = bundle.getBoolean("isCatalog");
        catalog = bundle.getString("catalog");
        page = 1;
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        sDialog();
        post();
        return load(view);
    }

    public View load(View view){
        lst = (ListView) view.findViewById(R.id.prodss);
        adapter = new PostAdapter(this.getContext(),R.layout.item_item, posts);
        lst.setAdapter(adapter);


        button = (ProgressBar) view.findViewById(R.id.button);
        lst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount && can)
                {
                    button.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        button.setProgress(50, true);
                        startAnimation();
                    }
                    can = false;
                    post();
                }
            }
        });
        return view;
    }


    private void post() {

            AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... urls) {
                    try {
                        try {
                            return HttpPost2("http://maltabu.kz/v1/api/clients/posts");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return "Error!";
                        }
                    } catch (IOException e) {
                        return "Unable to retrieve web page. URL may be invalid.";
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    try {
                        JSONObject Obj = new JSONObject(result);
                        resObj = Obj.getJSONArray("posts");
                        catalogList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            task.execute();
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    private JSONObject buidJsonObject2() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("byTime", true);
        if (this.isCatalog) {
            jsonObject.accumulate("catalogID", catalog);
        } else {
            jsonObject.accumulate("categoryID", catalog);
        }
        jsonObject.accumulate("countPosts", true);
        jsonObject.accumulate("increment", true);
        jsonObject.accumulate("page", this.page);

        if(Maltabu.filterModel!=null){
            FilterModel filter = Maltabu.filterModel;
            if(filter.getRegId()!=null){
                jsonObject.accumulate("regionID", filter.getRegId());
            }
            if(filter.getCityId()!=null){
                jsonObject.accumulate("cityID", filter.getCityId());
            }
            if(filter.getPrice1()!=null){
                jsonObject.accumulate("fromPrice", filter.getPrice1());
            }
            if(filter.getPrice2()!=null){
                jsonObject.accumulate("toPrice", filter.getPrice2());
            }
            jsonObject.accumulate("onlyImages", filter.isWithPhoto());
            jsonObject.accumulate("onlyExchange", filter.isBarter());
            jsonObject.accumulate("onlyEmergency", filter.isBargain());
        }

        page++;
        return jsonObject;
    }

    private String HttpPost2(String myUrl) throws IOException, JSONException {
        StringBuilder res = new StringBuilder();
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("isAuthorized", "false");
        conn.setDoOutput(true);
        JSONObject jsonObject = buidJsonObject2();
        setPostRequestContent(conn, jsonObject);
        conn.connect();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            res.append(line);
        }
        return res.toString();
    }

    private void catalogList() throws JSONException {
        Gson googleJson = new Gson();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
        for (int i = 0; i < postObjList.size(); i++) {
            JSONObject postObject = resObj.getJSONObject(i);
            JSONArray arr = postObject.getJSONArray("images");
            ArrayList<Image> imagesArrayList = new ArrayList<>();
            ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
            for (int j = 0; j < imgObjList.size(); j++) {
                JSONObject imgJson = arr.getJSONObject(j);
                Image image = new Image(
                        imgJson.getString("extra_small"),
                        imgJson.getString("small"),
                        imgJson.getString("medium"),
                        imgJson.getString("big"));
                imagesArrayList.add(image);
            }
            int visitors = postObject.getJSONObject("stat").getInt("visitors");
            String createdAt = postObject.getString("createdAt");
            String cityID = postObject.getJSONObject("cityID").getString("name");
            int number = postObject.getInt("number");
            String title = postObject.getString("title");
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
            if (postObject.getBoolean("hasContent")) {
                String content = postObject.getString("content");
                Post post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList);
                posts.add(post);
            } else {
                Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList);
                posts.add(post);
            }
        }
        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {}
        if (!can) {
            can = true;
            button.setVisibility(View.INVISIBLE);
        } else {
            epicDialog.dismiss();
        }
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void startAnimation(){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(button, "progress", 30000, 0);
        progressAnimator.setDuration(30000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    public String getDate(String s)    {
        String [] ss = s.split("T");
        String [] ss2 = ss[0].split("-");
        if (ss2[1].equals("01"))
        {
            ss2[1] = "Января,қаңтар";
        } else {
            if (ss2[1].equals("02"))
            {
                ss2[1] = "Февраля,ақпан";
            }
            else {
                if (ss2[1].equals("03"))
                {
                    ss2[1] = "Марта,наурыз";
                }
                else {
                    if (ss2[1].equals("04"))
                    {
                        ss2[1] = "Апреля,сәуiр";
                    } else {
                        if (ss2[1].equals("05"))
                        {
                            ss2[1] = "Мая,мамыр";
                        } else {
                            if (ss2[1].equals("06"))
                            {
                                ss2[1] = "Июня,маусым";
                            }
                            else {
                                if (ss2[1].equals("07"))
                                {
                                    ss2[1] = "Июля,шiлде";
                                } else {
                                    if (ss2[1].equals("08"))
                                    {
                                        ss2[1] = "Августа,тамыз";
                                    }
                                    else {
                                        if (ss2[1].equals("09"))
                                        {
                                            ss2[1] = "Сентября,қыркүйек";
                                        }
                                        else {
                                            if (ss2[1].equals("10"))
                                            {
                                                ss2[1] = "Октября,қазан";
                                            }
                                            else {
                                                if (ss2[1].equals("11"))
                                                {
                                                    ss2[1] = "Ноября,қараша";
                                                }
                                                else {
                                                    if (ss2[1].equals("12"))
                                                    {
                                                        ss2[1] = "Декабря,желтоқсан";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ss2[2] +","+ss2[1];
    }
}