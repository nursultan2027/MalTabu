package kz.maltabu.app.maltabukz.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.FilterActivity;
import kz.maltabu.app.maltabukz.adapters.PostRecycleAdapterNew;
import kz.maltabu.app.maltabukz.helpers.EndlessListener;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.FilterModel;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;

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
import java.util.concurrent.TimeUnit;


public class CategoryFragment extends Fragment {
    private JSONArray resObj;
    private ImageView imageView36;
    private TextView textView56,noPostsText;
    private String catalog;
    private int page;
    private ArrayList<Post> posts;
    private boolean isCatalog;
    private RecyclerView lst;
    private JSONObject object;
    private FloatingActionButton filterButton;
    private View view;
    private Dialog epicDialog;
    private ProgressBar button;
    private boolean can = true, promosAdded;
    private FileHelper fileHelper;
    private EndlessListener listener;
    private PostRecycleAdapterNew adapter;
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
        promosAdded = false;
        sDialog();
        view=inflater.inflate(R.layout.category_fragment, container, false);
        load(view);
        SecondThread thread = new SecondThread();
        thread.start();
        return view;
    }

    public View load(View view){

        button = (ProgressBar) view.findViewById(R.id.button);
        lst = (RecyclerView) view.findViewById(R.id.prodss);
        filterButton = (FloatingActionButton) view.findViewById(R.id.filterButton);
        adapter = new PostRecycleAdapterNew(posts, getActivity(),catalog);
        imageView36 = (ImageView) view.findViewById(R.id.imageView36);
        textView56 = (TextView) view.findViewById(R.id.textView56);
        noPostsText = (TextView) view.findViewById(R.id.noPostsText);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        lst.setHasFixedSize(true);
        lst.setAdapter(adapter);
        lst.setLayoutManager(manager);
        if(Maltabu.filterModel!=null){
            filterButton.setVisibility(View.VISIBLE);
            filterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), FilterActivity.class));
                    getActivity().finish();
                }
            });
        }
        else {
            filterButton.setVisibility(View.GONE);
        }
        setListener();
        return view;
    }

    private void setListener(){
        listener = new EndlessListener((LinearLayoutManager) lst.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                button.setVisibility(View.VISIBLE);
                button.setIndeterminate(true);
                can = false;
                SecondThread thread = new SecondThread();
                thread.start();
            }
        };
        lst.addOnScrollListener(listener);
    }


    private void post() {
            AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... urls) {
                    try {
                        try {
                            return HttpPost2("https://maltabu.kz/v1/api/clients/posts");
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
                            imageView36.setVisibility(View.VISIBLE);
                            textView56.setVisibility(View.VISIBLE);
                            noPostsText.setVisibility(View.VISIBLE);
                            if (epicDialog != null && epicDialog.isShowing()) {
                                epicDialog.dismiss();
                            }
                        } else {
                            imageView36.setVisibility(View.GONE);
                            textView56.setVisibility(View.GONE);
                            noPostsText.setVisibility(View.GONE);
                            resObj = Obj.getJSONArray("posts");
                            if (Obj.has("promos")) {
                                if(!promosAdded) {
                                    catalogList2(Obj.getJSONArray("promos"));
                                }
                            }
                            catalogList();
                        }
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
        if (this.isCatalog) {
            jsonObject.accumulate("catalogID", catalog);
        } else {
            jsonObject.accumulate("categoryID", catalog);
        }
        jsonObject.accumulate("byTime", Maltabu.byTime);
        jsonObject.accumulate("increment",Maltabu.increment);
        jsonObject.accumulate("countPosts", true);
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
        object = fileHelper.diction();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
        JSONObject postObject = new JSONObject();
        Image image = new Image();
        Comment com = new Comment();
        Post post = new Post();
        JSONObject imgJson = new JSONObject();
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
            JSONArray commentsArr = postObject.getJSONArray("comments");
            ArrayList<Comment> commentsArrayList = new ArrayList<>();
            ArrayList commObjList = googleJson.fromJson(String.valueOf(commentsArr), ArrayList.class);
            for (int k = 0; k < commObjList.size(); k++) {
                com = new Comment();
                commentsArrayList.add(com);
            }
            int visitors = postObject.getJSONObject("stat").getInt("visitors");
            String createdAt = postObject.getString("createdAt");
            String cityID = postObject.getJSONObject("cityID").getString("name");
            int number = postObject.getInt("number");
            String title = postObject.getString("title");
            String price = postObject.getJSONObject("price").getString("kind");
            if (price.equals("value")) {
                price = uiPrice(String.valueOf(postObject.getJSONObject("price").getInt("value")))+" ₸";
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
                post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                posts.add(post);
            } else {
                post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                posts.add(post);
            }
        }
        try {
            lst.invalidate();
            adapter.notifyDataSetChanged();
            lst.refreshDrawableState();
        } catch (Exception e) {}
        if (!can) {
            can = true;
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                button.setVisibility(View.INVISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (epicDialog != null && epicDialog.isShowing()) {
                epicDialog.dismiss();
            }
        }
    }
    private void catalogList2(JSONArray promosArr) throws JSONException {
        Gson googleJson = new Gson();
        object = fileHelper.diction();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(promosArr), ArrayList.class);
        JSONObject postObject = new JSONObject();
        Image image = new Image();
        Comment com = new Comment();
        Post post = new Post();
        JSONObject imgJson = new JSONObject();
        for (int i = 0; i < postObjList.size(); i++) {
            postObject = promosArr.getJSONObject(i);
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
            JSONArray commentsArr = postObject.getJSONArray("comments");
            ArrayList<Comment> commentsArrayList = new ArrayList<>();
            ArrayList commObjList = googleJson.fromJson(String.valueOf(commentsArr), ArrayList.class);
            for (int k = 0; k < commObjList.size(); k++) {
                com = new Comment();
                commentsArrayList.add(com);
            }
            int visitors = postObject.getJSONObject("stat").getInt("visitors");
            String createdAt = postObject.getString("createdAt");
            String cityID = postObject.getJSONObject("cityID").getString("name");
            int number = postObject.getInt("number");
            String title = postObject.getString("title");
            String price = postObject.getJSONObject("price").getString("kind");
            if (price.equals("value")) {
                price = uiPrice(String.valueOf(postObject.getJSONObject("price").getInt("value")))+" ₸";
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
                post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList, true);
                posts.add(post);
            } else {
                post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList, true);
                posts.add(post);
            }
        }
        try {
            lst.invalidate();
            adapter.notifyDataSetChanged();
            lst.refreshDrawableState();
        } catch (Exception e) {}
        if (!can) {
            can = true;
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                button.setVisibility(View.INVISIBLE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (epicDialog != null && epicDialog.isShowing()) {
                epicDialog.dismiss();
            }
        }
        promosAdded=true;
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    public String uiPrice(String value){
        int length = value.length();
        String result = value;
        if(length>3){
            if(length==4){
                result = value.substring(0,1)+" "+value.substring(1);
            } else {
                if(length==5){
                    result = value.substring(0,2)+" "+value.substring(2);
                } else {
                    if(length==6){
                        result = value.substring(0,3)+" "+value.substring(3);
                    } else {
                        if(length==7){
                            result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4);
                        } else {
                            if(length==8){
                                result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5);
                            } else {
                                if(length==9){
                                    result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6);
                                } else {
                                    if(length==10){
                                        result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7);
                                    } else {
                                        if(length==11){
                                            result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8);
                                        } else {
                                            if(length==12){
                                                result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9);
                                            } else {
                                                if(length==13){
                                                    result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7,10)+" "+value.substring(10);
                                                } else {
                                                    if(length==14){
                                                        result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8,11)+" "+value.substring(11);
                                                    } else {
                                                        if(length==15){
                                                            result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9,12)+" "+value.substring(12);
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
        }

        return result;
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

    public class SecondThread extends Thread{
        SecondThread(){}

        @Override
        public void run() {
            post();
        }
    }

}