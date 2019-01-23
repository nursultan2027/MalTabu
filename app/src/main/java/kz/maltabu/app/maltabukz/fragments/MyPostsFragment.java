package kz.maltabu.app.maltabukz.fragments;

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

import com.google.gson.Gson;
import com.maltabu.app.R;

import kz.maltabu.app.maltabukz.adapters.MyPostsAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.models.PostAtMyPosts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyPostsFragment extends Fragment {
    private View view;
    private Spinner spinner;
    private JSONObject object;
    private FileHelper fileHelper;
    private TextView txt;
    private ImageView img;
    private ListView prodss;
    private MyPostsAdapter adapter1, adapter2, adapter3, adapter4;
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
        arr.add("Активные объявления");
        arr.add("Объявления на модерации");
        arr.add("Объявления в архиве");
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
            Gson googleJson = new Gson();
            JSONArray resObj = object.getJSONArray("ads");
            ArrayList transObjList = googleJson.fromJson(String.valueOf(resObj), ArrayList.class);
            String catalogID="";
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
//                myPosts.add(postAtMyPosts);
                if(status.equals("rejected")){
                    myPostsRej.add(postAtMyPosts);
                } else {
                    if(status.equals("waiting")) {
                        myPostsWait.add(postAtMyPosts);
                    } else {
                        if(status.equals("confirmed")){
                            myPostsActiv.add(postAtMyPosts);
                        } else{
                            if(status.equals("recovery")){
                                myPostsArch.add(postAtMyPosts);
                            }
                        }
                    }
                }
            }
            adapter1 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsRej);
            adapter2 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsActiv);
            adapter3 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsArch);
            adapter4 = new MyPostsAdapter(getActivity(),R.layout.my_posts_item,myPostsWait);
            prodss.setAdapter(adapter1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}