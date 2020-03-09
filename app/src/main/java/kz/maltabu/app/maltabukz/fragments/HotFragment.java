package kz.maltabu.app.maltabukz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yandex.metrica.YandexMetrica;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.RecycleHotAdapterNew;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotFragment extends Fragment {
    private JSONArray jsonArray;
    private Dialog epicDialog;
    private RecyclerView recyclerView;
    private ImageView banner;
    private RecycleHotAdapterNew myAdapter;
    private SwipeRefreshLayout refreshLayout;
    private FileHelper fileHelper;
    private ArrayList<Post> posts=new ArrayList<>();

    public HotFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        epicDialog = new Dialog(getActivity());
        fileHelper = new FileHelper(getActivity());
        sDialog();
        post();
        Resources resources = getActivity().getResources();
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.hots);
        banner = (ImageView) view.findViewById(R.id.reclama);
        Context context = LocaleHelper.setLocale(getActivity(), Maltabu.lang);
        myAdapter = new RecycleHotAdapterNew(posts,getActivity());
        String url = fileHelper.getBanner();
        refreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.MaltabuYellow), getActivity().getResources().getColor(R.color.MaltabuBlue), getActivity().getResources().getColor(R.color.MaltabuGreen));
        if(!url.isEmpty()) {
            if (url.endsWith("gif")) {
                if(url.startsWith("http"))
                    Glide.with(getActivity()).asGif().load(url).into(banner);
                else
                    Glide.with(getActivity()).asGif().load("https://maltabu.kz/" + url).into(banner);
            }
            else {
                if(url.startsWith("http"))
                    Picasso.with(getActivity()).load(url).fit().into(banner);
                else
                    Picasso.with(getActivity()).load("https://maltabu.kz/" + url).fit().into(banner);
            }
        }
        else {
            banner.setVisibility(View.GONE);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                posts.clear();
                recyclerView.setVisibility(View.GONE);
                post();
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,1);
        manager.setGapStrategy(2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(false);
        setListeners();
        return view;
    }

    private void setListeners(){
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventParameters = "{\"platform\":\"Android\", \"version\":\"1.0.56\"}";
                YandexMetrica.reportEvent("Banner Clicked", eventParameters);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileHelper.getTarget()));
                startActivity(browserIntent);
            }
        });
    }

    private String CutString(String str){
        return str.substring(0, 11)+"...";
    }
    private void post() {

        String url = "https://maltabu.kz/v1/api/clients/data/hot";
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
        for (int i = 0; i < 12; i++) {
            JSONObject postObject = jsonArray.getJSONObject(i);
            JSONArray arr=null;
            if(postObject.has("img")) {
                JSONObject imgWebObject = postObject.getJSONObject("img");
                if(imgWebObject.has("web")) {
                    arr = imgWebObject.getJSONArray("web");
                }
            } else {
                arr = postObject.getJSONArray("images");
            }
            ArrayList<Image> imagesArrayList = new ArrayList<>();
            if(arr!=null) {
                ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                for (int j = 0; j < imgObjList.size(); j++) {
                    JSONObject imgJson = arr.getJSONObject(j);
                    Image image = new Image(imgJson.getString("small"));
                    imagesArrayList.add(image);
                }
            }
            String cityID = postObject.getJSONObject("cityID").getString("name");
            int number = postObject.getInt("number");
            String price = postObject.getJSONObject("price").getString("kind");
            if (price.equals("value")) {
                price = uiPrice(String.valueOf(postObject.getJSONObject("price").getInt("value")))+" ₸";
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
        refreshLayout.setRefreshing(false);
        myAdapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
        if (getActivity()!=null && !getActivity().isFinishing() && epicDialog != null) {
            epicDialog.dismiss();
        }
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

}
