package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.CatalogFragment;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.CategoryFragment;
import com.proj.changelang.models.Region;
import com.proj.changelang.models.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private JSONObject ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asd);
        if (isConnected())
        {
            GetCategories();
            GetDictionary();
        }
    }
    public void GetCategories() {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/data")
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
                        ff = new JSONObject(s1);
                        categsList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
    public void categsList() throws JSONException {
        Gson googleJson = new Gson();
        ArrayList<Region> regionArrayList = new ArrayList<>();
        JSONArray regions = ff.getJSONArray("regions");
        ArrayList jsonObjList = googleJson.fromJson(String.valueOf(regions), ArrayList.class);
        for (int i=0; i<jsonObjList.size(); i++)
        {
            JSONObject categoryObject = regions.getJSONObject(i);
            Region regionRegion = new Region(categoryObject.getString("_id"), categoryObject.getString("value"), categoryObject.getString("name"), categoryObject.getString("firstCase"));
            ArrayList<City> cityArrayList = new ArrayList<>();
            JSONArray cities = categoryObject.getJSONArray("cities");
            ArrayList jsonObjList2 = googleJson.fromJson(String.valueOf(cities), ArrayList.class);
            for(int j=0; j<jsonObjList2.size(); j++) {
                JSONObject cityObject = cities.getJSONObject(j);
                City city = new City(cityObject.getString("_id"), cityObject.getString("value"), cityObject.getString("name"), cityObject.getString("firstCase"));
                cityArrayList.add(city);
            }
            regionRegion.cities = cityArrayList;
            regionArrayList.add(regionRegion);
        }
        Maltabu.regions = regionArrayList;

        ArrayList<Category> categoryArrayList= new ArrayList<>();
        JSONArray categs = ff.getJSONArray("categories");
        ArrayList categsList = googleJson.fromJson(String.valueOf(categs), ArrayList.class);
        for(int k=0; k<categsList.size();k++)
        {
            JSONObject regionObject = categs.getJSONObject(k);
            int count = regionObject.getJSONObject("stat").getInt("count");
            Category category = new Category(regionObject.getString("_id"), regionObject.getString("value"), regionObject.getString("name"), regionObject.getString("firstCase"),count);
            ArrayList<Catalog> catalogsArrayList = new ArrayList<>();
            JSONArray catalogs = regionObject.getJSONArray("catalogs");
            ArrayList jsonObjList3 = googleJson.fromJson(String.valueOf(catalogs), ArrayList.class);
            for (int l=0; l<jsonObjList3.size();l++)
            {
                JSONObject catalogObj = catalogs.getJSONObject(l);
                int count2 = catalogObj.getJSONObject("stat").getInt("count");
                Catalog catalog = new Catalog(catalogObj.getString("_id"), catalogObj.getString("value"), catalogObj.getString("name"), catalogObj.getString("firstCase"),count2);
                catalogsArrayList.add(catalog);
            }
            category.catalogs=catalogsArrayList;
            categoryArrayList.add(category);
        }
        Maltabu.categories = categoryArrayList;
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }
    public void GetDictionary() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://maltabu.kz/dist/translations/kk_KZ.json")
                .get()
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        Maltabu.jsonObject = new JSONObject(s).getJSONObject("kk_KZ");
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
}
