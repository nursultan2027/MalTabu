package com.proj.changelang.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.activities.FilterActivity;
import com.proj.changelang.activities.FirstSelect1;
import com.proj.changelang.activities.MainActivity2;
import com.proj.changelang.activities.SecondSelect1;
import com.proj.changelang.adapters.PostRecycleAdapter;
import com.proj.changelang.helpers.EndlessListener;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SearchFragment extends Fragment {
    private JSONArray resObj;
    private JSONObject object;
    private FileHelper fileHelper;
    private Dialog epicDialog;
    private Button btn1, btn2, search;
    private ImageView img;
    private TextView title, text;
    private EditText editText;
    private int page;
    private View view;
    private RecyclerView lst;
    private PostRecycleAdapter adapter;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        page = 1;
        epicDialog = new Dialog(getActivity());
        btn1 = view.findViewById(R.id.button2);
        btn2 = view.findViewById(R.id.button3);
        img = (ImageView) view.findViewById(R.id.imageView36);
        title = (TextView) view.findViewById(R.id.textView56);
        text = (TextView) view.findViewById(R.id.noPostsText);
        fileHelper = new FileHelper(getActivity());
        search = view.findViewById(R.id.button5);
        editText = view.findViewById(R.id.editText9);
        lst = view.findViewById(R.id.prods);
        adapter = new PostRecycleAdapter(posts,getActivity());
        lst.setLayoutManager(new LinearLayoutManager(getActivity()));
        lst.setAdapter(adapter);
        lst.setHasFixedSize(false);
        Context context = LocaleHelper.setLocale(getActivity(), Maltabu.lang);
        Resources res = context.getResources();
        title.setText(res.getString(R.string.noPostTitle));
        text.setText(res.getString(R.string.noPostText));
        btn1.setText(res.getString(R.string.Option1));
        btn2.setText(res.getString(R.string.Option2));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty())
                {
                    Maltabu.text = editText.getText().toString();
                }
                getActivity().startActivity(new Intent(getActivity(), FirstSelect1.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty())
                {
                    Maltabu.text = editText.getText().toString();
                }
                getActivity().startActivity(new Intent(getActivity(), SecondSelect1.class));
            }
        });
        if (Maltabu.s2 != null) {
            btn1.setText(Maltabu.s2);
        }
        if (Maltabu.s4 != null) {
            btn2.setText(Maltabu.s4);
        }
        if(Maltabu.text!=null){
            editText.setText(Maltabu.text);
        } else {
            editText.setHint(res.getString(R.string.SearchText2));
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog();
                post();
                Maltabu.text = editText.getText().toString();
            }
        });

        if(Maltabu.byTime==false||Maltabu.increment==false){
            post();
        }
        return view;
    }

    public View load(View view) {
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
                    if(Obj.getInt("count")==0)
                    {
                        epicDialog.dismiss();
                        img.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                    else {
                        img.setVisibility(View.GONE);
                        title.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        resObj = Obj.getJSONArray("posts");
                        catalogList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        task.execute();
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
        if (!editText.getText().toString().isEmpty())
            jsonObject.accumulate("text", editText.getText().toString());
        if (Maltabu.s1 != null)
            jsonObject.accumulate("catalogID", Maltabu.s1);
        if (Maltabu.s3 != null)
            jsonObject.accumulate("cityID", Maltabu.s3);
        jsonObject.accumulate("byTime", Maltabu.byTime);
        jsonObject.accumulate("increment", Maltabu.increment);
        jsonObject.accumulate("countPosts", true);
        jsonObject.accumulate("page", this.page);
        jsonObject.accumulate("onlyEmergency", false);
        jsonObject.accumulate("onlyExchange", false);
        jsonObject.accumulate("onlyImages", false);
        page++;
        return jsonObject;
    }

    private void catalogList() throws JSONException {
        Gson googleJson = new Gson();
        object = fileHelper.diction();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
        JSONObject postObject = new JSONObject();
        JSONObject imgJson = new JSONObject();
        Image image = new Image();
        Post post = new Post();
        for (int i = 0; i < postObjList.size(); i++) {
            postObject = resObj.getJSONObject(i);
            ArrayList<Image> imagesArrayList = new ArrayList<>();
            JSONArray arr = postObject.getJSONArray("images");
            ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
            for (int j = 0; j < imgObjList.size(); j++) {
                imgJson = arr.getJSONObject(j);
                image = new Image(
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
                        String kazName = object.getString("Договорная цена");
                        price = kazName;
                    }
                } else {
                    if (price.equals("free")) {
                        if (Maltabu.lang.toLowerCase().equals("ru")) {
                            price = "Отдам даром";
                        } else {
                            String kazName = object.getString("Отдам даром");
                            price = kazName;
                        }
                    }
                }
            }
            if (postObject.getBoolean("hasContent")) {
                String content = postObject.getString("content");
                post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList);
                posts.add(post);
            } else {
                post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList);
                posts.add(post);
            }
        }
        lst.invalidate();
        adapter.notifyDataSetChanged();
        lst.refreshDrawableState();
        epicDialog.dismiss();
    }


    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
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