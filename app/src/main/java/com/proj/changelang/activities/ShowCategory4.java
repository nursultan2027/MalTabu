package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.CategoryFragment;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Item;
import com.proj.changelang.models.Post;

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

public class ShowCategory4 extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Category category;
    private View headerView;
    private Dialog epicDialog;
    private JSONArray resObj;
    private ArrayList< ArrayList<Post> >  posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_catalog);
        category = getIntent().getParcelableExtra(Category.class.getCanonicalName());
        epicDialog = new Dialog(this);
        posts.add(new ArrayList<Post>());
        posts.add(new ArrayList<Post>());
        posts.add(new ArrayList<Post>());
        posts.add(new ArrayList<Post>());
        posts.add(new ArrayList<Post>());
        posts.add(new ArrayList<Post>());
        if(Maltabu.lang.toLowerCase().equals("ru")) {
            setTitle(category.getName());
        } else {
            String kazName = null;
            try {
                kazName = Maltabu.jsonObject.getString(category.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setTitle(kazName);
        }
        sDialog();
        HTTPAsyncTask task = new HTTPAsyncTask();
        HTTPAsyncTask2 task1 = new HTTPAsyncTask2();
        HTTPAsyncTask3 task2 = new HTTPAsyncTask3();
        task.execute();
        task1.execute();
        task2.execute();


//        final Resources resources = getResources();
//        final int [] imgRes = new int[]{R.id.imageView10,R.id.imageView11,R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16};
//        int [] conRes = new int[]{R.id.con1,R.id.con2,R.id.con3,R.id.con4,R.id.con5,R.id.con6,R.id.con7};
//        final int [] textRes = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7};
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()-1]);
//                int tabIconColor = ContextCompat.getColor(ShowCategory.this, R.color.MaltabuBlue);
//                img.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                TextView txt = (TextView) tab.getCustomView().findViewById(textRes[tab.getPosition()-1]);
//                txt.setTextColor(resources.getColor(R.color.MaltabuBlue));
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()+1]);
//                int tabIconColor = ContextCompat.getColor(ShowCategory.this, R.color.MaltabuYellow);
//                img.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                TextView txt = (TextView) tab.getCustomView().findViewById(textRes[tab.getPosition()+1]);
//                txt.setTextColor(resources.getColor(R.color.MaltabuYellow));
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//             }
//        });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void printWhiteBoxes() {
        Resources resources = this.getResources();
        int [] imgRes = new int[]{R.id.imageView10,R.id.imageView11,R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16};
        int [] conRes = new int[]{R.id.con1,R.id.con2,R.id.con3,R.id.con4,R.id.con5,R.id.con6,R.id.con7};
        int [] textRes = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7};
        for(int i=0; i< tabLayout.getTabCount()-1; i++){
            ImageView img = (ImageView) headerView.findViewById(imgRes[i]);
            img.setImageResource(R.drawable.ic_action_na);
            TextView txt = (TextView) headerView.findViewById(textRes[i]);
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                txt.setText(category.catalogs.get(i).getName());
            } else {
                try {
                    String kame = Maltabu.jsonObject.getString(category.catalogs.get(i).getName());
                    txt.setText(kame);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ConstraintLayout con = (ConstraintLayout) headerView.findViewById(conRes[i]);
            tabLayout.getTabAt(i+1).setCustomView(con);
        }

        TextView txt = (TextView) headerView.findViewById(R.id.text);
        txt.setText("все");
        ConstraintLayout con = (ConstraintLayout) headerView.findViewById(R.id.con);
        tabLayout.getTabAt(0).setCustomView(con);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle1 = new Bundle();
        bundle1.putParcelableArrayList("posts", posts.get(0));
        CategoryFragment fragobj1=new CategoryFragment();
        fragobj1.setArguments(bundle1);

        if(Maltabu.lang.toLowerCase().equals("ru")) {
            adapter.addFragment(fragobj1, "все");
        } else {
            adapter.addFragment(fragobj1, "барлық");
        }
        for (int i=0; i<category.catalogs.size();i++){
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList("posts", posts.get(i+1));
            CategoryFragment fragobj2 =new CategoryFragment();
            fragobj2.setArguments(bundle2);
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                adapter.addFragment(fragobj2, category.catalogs.get(i).getName());
            } else {
                try {
                    String kazName = Maltabu.jsonObject.getString(category.catalogs.get(i).getName());
                    adapter.addFragment(fragobj2, kazName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        viewPager.setAdapter(adapter);
    }


    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return HttpPost("http://maltabu.kz/v1/api/clients/posts");
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
                catalogList(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private String HttpPost(String myUrl) throws IOException, JSONException {
        StringBuilder res = new StringBuilder();
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("isAuthorized", "false");
        conn.setDoOutput(true);
        JSONObject jsonObject = buidJsonObject();
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
    private JSONObject buidJsonObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("byTime", true);
        jsonObject.accumulate("categoryID", "5ab672c9559d5e049c25a639");
        jsonObject.accumulate("countPosts", true);
        jsonObject.accumulate("increment", true);
        jsonObject.accumulate("onlyEmergency", false);
        jsonObject.accumulate("onlyExchange", false);
        jsonObject.accumulate("onlyImages",  false);
        jsonObject.accumulate("page",  1);
        return jsonObject;
    }
    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    private class HTTPAsyncTask2 extends AsyncTask<String, Void, String> {
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
                catalogList(1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
    private JSONObject buidJsonObject2() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("byTime", true);
        jsonObject.accumulate("catalogID", "5ab672c9559d5e049c25a63a");
        jsonObject.accumulate("countPosts", true);
        jsonObject.accumulate("increment", true);
        jsonObject.accumulate("onlyEmergency", false);
        jsonObject.accumulate("onlyExchange", false);
        jsonObject.accumulate("onlyImages",  false);
        jsonObject.accumulate("page",  1);
        return jsonObject;
    }

    private class HTTPAsyncTask3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return HttpPost3("http://maltabu.kz/v1/api/clients/posts");
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
                catalogList(2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private String HttpPost3(String myUrl) throws IOException, JSONException {
        StringBuilder res = new StringBuilder();
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("isAuthorized", "false");
        conn.setDoOutput(true);
        JSONObject jsonObject = buidJsonObject3();
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
    private JSONObject buidJsonObject3() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("byTime", true);
        jsonObject.accumulate("catalogID", "5ab672c9559d5e049c25a63b");
        jsonObject.accumulate("countPosts", true);
        jsonObject.accumulate("increment", true);
        jsonObject.accumulate("onlyEmergency", false);
        jsonObject.accumulate("onlyExchange", false);
        jsonObject.accumulate("onlyImages",  false);
        jsonObject.accumulate("page",  1);
        return jsonObject;
    }


    private void catalogList(int numb) throws JSONException {
        Gson googleJson = new Gson();
        ArrayList postObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
        for (int i=0; i<postObjList.size(); i++)
        {
            JSONObject postObject = resObj.getJSONObject(i);
            JSONArray arr = postObject.getJSONArray("images");
            ArrayList<Image> imagesArrayList = new ArrayList<>();
            ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
            for (int j=0; j<imgObjList.size();j++)
            {
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
            String postPrice = null;
            if(price.equals("value")) {
                price = String.valueOf(postObject.getJSONObject("price").getInt("value"));
            }
            else {
                if(price.equals("trade")){
                    if(Maltabu.lang.toLowerCase().equals("ru")) {
                        price = "Договорная цена";
                    } else {
                        String kazName = Maltabu.jsonObject.getString("Договорная цена");
                        price = kazName;
                    }
                }
                else {
                    if (price.equals("free")){
                        if(Maltabu.lang.toLowerCase().equals("ru")) {
                            price = "Отдам даром";
                        } else {
                            String kazName = Maltabu.jsonObject.getString("Отдам даром");
                            price = kazName;
                        }
                    }
                }
            }
            if (postObject.getBoolean("hasContent")){
                String content = postObject.getString("content");
                Post post = new Post(visitors, createdAt, title,content, cityID, price, String.valueOf(number), imagesArrayList);
                posts.get(numb).add(post);
            } else {
                Post post = new Post(visitors, createdAt, title, cityID, price, String.valueOf(number), imagesArrayList);
                posts.get(numb).add(post);
            }
        }
        if(numb==2){
            viewInit();
            epicDialog.dismiss();
        }
    }
    private void viewInit(){
        headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_item, null, false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        printWhiteBoxes();
    }
    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
