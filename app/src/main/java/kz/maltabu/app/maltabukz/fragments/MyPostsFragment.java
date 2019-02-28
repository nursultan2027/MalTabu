package kz.maltabu.app.maltabukz.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.activities.AuthAvtivity;
import kz.maltabu.app.maltabukz.activities.CabinetActivity;
import kz.maltabu.app.maltabukz.adapters.MyPostsAdapter;
import kz.maltabu.app.maltabukz.adapters.MyPostsAdapterActive;
import kz.maltabu.app.maltabukz.adapters.MyPostsAdapterArch;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.PostAtMyPosts;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class MyPostsFragment extends Fragment {
    private View view;
    private Spinner spinner;
    private JSONObject object, dictionary;
    private FileHelper fileHelper;
    private TextView txt;
    private ImageView img;
    private ListView prodss;
    private MyPostsAdapter adapter1, adapter4;
    private MyPostsAdapterActive  adapter2;
    private MyPostsAdapterArch adapter3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_posts_fragment, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        fileHelper = new FileHelper(getActivity());
        prodss = (ListView) view.findViewById(R.id.prodss);
        txt = (TextView) view.findViewById(R.id.textView56);
        img = (ImageView) view.findViewById(R.id.imageView36);
        categList();
        ArrayList<String> arr = new ArrayList<>();
        if(Maltabu.lang.equals("ru")) {
            arr.add("Активные объявления");
            arr.add("Объявления на модерации");
            arr.add("Объявления в архиве");
        }
        else {
            arr.add("Ағымдағы хабарландырулар");
            arr.add("Модерацияға жіберілген хабарландырулар");
            arr.add("Архивтегі хабарландырулар");
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    prodss.setAdapter(adapter2);
                    if(adapter2.getCount()<1){
                        img.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                    } else {
                        img.setVisibility(View.GONE);
                        txt.setVisibility(View.GONE);
                    }
                } else {
                    if(position==1){
                        prodss.setAdapter(adapter4);
                        if(adapter4.getCount()<1){
                            img.setVisibility(View.VISIBLE);
                            txt.setVisibility(View.VISIBLE);
                        } else {
                            img.setVisibility(View.GONE);
                            txt.setVisibility(View.GONE);
                        }
                    } else {
                        if(position==2){
                            prodss.setAdapter(adapter3);
                            if(adapter3.getCount()<1){
                                img.setVisibility(View.VISIBLE);
                                txt.setVisibility(View.VISIBLE);
                            } else {
                                img.setVisibility(View.GONE);
                                txt.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }


    public void categList() {
        ArrayList<PostAtMyPosts> myPostsWait = new ArrayList<>();
        ArrayList<PostAtMyPosts> myPostsActiv = new ArrayList<>();
        ArrayList<PostAtMyPosts> myPostsArch = new ArrayList<>();
        ArrayList<PostAtMyPosts> myPostsRej = new ArrayList<>();
        try {
            object = new JSONObject(fileHelper.readPostingFile());
            dictionary = fileHelper.diction();
            Gson googleJson = new Gson();
            JSONArray resObj = object.getJSONArray("ads");
            ArrayList transObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
            String name="";
            String createdAt="";
            String status="";
            String title="";
            String imgJson = "";
            JSONObject obj = null;
            PostAtMyPosts postAtMyPosts = null;
            for(int i=0; i<transObjList.size();i++){
                JSONObject postsJsonObject = resObj.getJSONObject(i);
                ArrayList<String> imagesArrayList = new ArrayList<>();
                JSONArray arr = postsJsonObject.getJSONArray("images");
                ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                for (int j = 0; j < imgObjList.size(); j++) {
                    imgJson = arr.getString(j);
                    imagesArrayList.add(imgJson);
                }
                obj = postsJsonObject.getJSONObject("catalogID");
                name = obj.getString("name");
                createdAt = postsJsonObject.getString("createdAt");
                status = postsJsonObject.getString("status");
                title = postsJsonObject.getString("title");
                postAtMyPosts = new PostAtMyPosts(name,createdAt,status,title);
                if(imagesArrayList.size()>0){
                    postAtMyPosts.setImg(imagesArrayList);
                }
                if(status.equals("rejected")){
                    myPostsRej.add(postAtMyPosts);
                } else {
                    if(status.equals("waiting")) {
                        myPostsWait.add(postAtMyPosts);
                    }
                }
            }


            JSONArray resObj2 = object.getJSONArray("posts");
            ArrayList transObjList2 = googleJson.fromJson(String.valueOf(resObj2), ArrayList.class);
            PostAtMyPosts postAtMyPosts2 = null;
            String catalogID="";
            String categoryID="";
            String number="";
            String price="";
            String visitors="";
            String phones="";
            String comments="";
            for(int i=0; i<transObjList2.size();i++){
                JSONObject postsJsonObject = resObj2.getJSONObject(i);
                ArrayList<String> imagesArrayList = new ArrayList<>();
                if(postsJsonObject.has("images")) {
                    JSONArray arr = postsJsonObject.getJSONArray("images");
                    ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                    for (int j = 0; j < imgObjList.size(); j++) {
                        imgJson = arr.getString(j);
                        imagesArrayList.add(imgJson);
                    }
                }
                JSONArray arr = postsJsonObject.getJSONArray("comments");
                ArrayList commObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                comments = String.valueOf(commObjList.size());
                visitors = String.valueOf(postsJsonObject.getJSONObject("stat").getInt("phone"));
                phones = String.valueOf(postsJsonObject.getJSONObject("stat").getInt("visitors"));
                obj = postsJsonObject.getJSONObject("catalogID");
                catalogID = obj.getString("name");
                obj = postsJsonObject.getJSONObject("categoryID");
                categoryID = obj.getString("name");
                createdAt = postsJsonObject.getString("createdAt");
                status = postsJsonObject.getString("status");
                title = postsJsonObject.getString("title");
                price = postsJsonObject.getJSONObject("price").getString("kind");
                if (price.equals("value")) {
                    price = String.valueOf(postsJsonObject.getJSONObject("price").getInt("value"))+" ₸";
                } else {
                    if (price.equals("trade")) {
                        price = "Договорная цена";
                    } else {
                        if (price.equals("free")) {
                            price = "Отдам даром";
                        }
                    }
                }
                number = String.valueOf(postsJsonObject.getInt("number"));
                postAtMyPosts2 = new PostAtMyPosts(visitors, comments, phones, number,price,catalogID,categoryID,createdAt,status,title);
                if(imagesArrayList.size()>0){
                    postAtMyPosts2.setImg(imagesArrayList);
                }
                if(status.equals("archived")){
                    myPostsArch.add(postAtMyPosts2);
                } else {
                    if(status.equals("active")) {
                        myPostsActiv.add(postAtMyPosts2);
                    }
                }
            }
            adapter1 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsRej);
            Collections.reverse(myPostsActiv);
            Collections.reverse(myPostsArch);
            adapter2 = new MyPostsAdapterActive(getActivity(),R.layout.my_posts_activ_item,myPostsActiv);
            adapter3 = new MyPostsAdapterArch(getActivity(),R.layout.my_posts_arch_item,myPostsArch);
            adapter4 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsWait);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}