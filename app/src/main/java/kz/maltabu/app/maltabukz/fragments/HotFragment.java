package kz.maltabu.app.maltabukz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.RecycleHotAdapter;
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
    private RecycleHotAdapter myAdapter;
    private FileHelper fileHelper;
    private ArrayList<Post> posts=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        epicDialog = new Dialog(getActivity());
        fileHelper = new FileHelper(getActivity());
        sDialog();
        post();
        Resources resources = getActivity().getResources();
        recyclerView = (RecyclerView) view.findViewById(R.id.hots);
        Context context = LocaleHelper.setLocale(getActivity(), Maltabu.lang);
        myAdapter = new RecycleHotAdapter(posts,getActivity());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,1);
        manager.setGapStrategy(2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(false);
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
                price = String.valueOf(postObject.getJSONObject("price").getInt("value"))+" ₸";
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
        myAdapter.notifyDataSetChanged();
        if (epicDialog != null && epicDialog.isShowing()) {
            epicDialog.dismiss();
        }
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }



}
