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
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView menu1, menu2,menu3,menu4,menu6,menu5,menu7,menu8, menu82, cab;
    private Button select1,select2;
    private EditText editText;
    private JSONObject ff;
    private Dialog epicDialog;
    private Intent nextSelect, nextSelect2, nextSelect3, nextSelect4, nextSelect5, nextSelect6, nextSelect7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActivityView();
        epicDialog = new Dialog(this);
        sDialog();
        if (isConnected())
        {
            GetCategories();
            GetDictionary();
        }
    }

    private void SetActivityView(){
        setContentView(R.layout.activity_main);
        select1 = (Button) findViewById(R.id.select1);
        select2 = (Button) findViewById(R.id.select2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Paper.init(this);
        String language = Paper.book().read("language");

        if(language==null){
            Paper.book().write("language", "ru");
            Maltabu.lang = "ru";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        cab  = (TextView) view.findViewById(R.id.cabinet);
        menu1  = (TextView) view.findViewById(R.id.menu1);
        menu2  = (TextView) view.findViewById(R.id.menu2);
        menu3  = (TextView) view.findViewById(R.id.menu3);
        menu4  = (TextView) view.findViewById(R.id.menu4);
        menu5  = (TextView) view.findViewById(R.id.menu5);
        menu6  = (TextView) view.findViewById(R.id.menu6);
        menu7  = (TextView) view.findViewById(R.id.menu7);
        menu8  = (TextView) findViewById(R.id.menu8);
        menu82  = (TextView) findViewById(R.id.menu82);
        editText = (EditText) findViewById(R.id.search);

        initListeners();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        updateView((String) Paper.book().read("language"));
        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FirstSelect1.class));
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondSelect1.class));
            }
        });
    }
    private void initListeners() {
        nextSelect = new Intent(this, ShowCategory.class);
        nextSelect2 = new Intent(this, ShowCategory2.class);
        nextSelect3 = new Intent(this, ShowCategory3.class);
        nextSelect4 = new Intent(this, ShowCategory4.class);
        nextSelect5 = new Intent(this, ShowCategory5.class);
        nextSelect6 = new Intent(this, ShowCategory6.class);
        nextSelect7 = new Intent(this, ShowCategory7.class);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(1));
                startActivity(nextSelect);
            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect2.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(4));
                startActivity(nextSelect2);
            }
        });
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect3.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(2));
                startActivity(nextSelect3);
            }
        });
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect4.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(0));
                startActivity(nextSelect4);
            }
        });
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect5.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(3));
                startActivity(nextSelect5);
            }
        });
        menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect6.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(5));
                startActivity(nextSelect6);
            }
        });
        menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSelect7.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(6));
                startActivity(nextSelect7);
            }
        });

    }


    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        setTitle("Малтабу");
        if (Maltabu.s2==null)
        {
            select1.setText(resources.getString(R.string.Option1));
        } else
        {
            select1.setText(Maltabu.s2);
        }

        if(Maltabu.s4==null)
        {
            select2.setText(resources.getString(R.string.Option2));
        }else
        {
            select2.setText(Maltabu.s4);
        }
        cab.setText(resources.getString(R.string.Cabinet));
        menu1.setText(resources.getString(R.string.menu1));
        menu2.setText(resources.getString(R.string.menu2));
        menu3.setText(resources.getString(R.string.menu3));
        menu4.setText(resources.getString(R.string.menu4));
        menu5.setText(resources.getString(R.string.menu5));
        menu6.setText(resources.getString(R.string.menu6));
        menu7.setText(resources.getString(R.string.menu7));
        menu8.setText(resources.getString(R.string.menu8));
        menu82.setText(resources.getString(R.string.menu82));
        editText.setHint(resources.getString(R.string.Search));
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ru) {
            Paper.book().write("language", "ru");
            Maltabu.lang = "ru";
            updateView((String)Paper.book().read("language"));
        }
        else if (item.getItemId() == R.id.menu_kz){
            Paper.book().write("language", "kk");
            Maltabu.lang = "kk";
            updateView((String)Paper.book().read("language"));
        }

        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"ru"));
    }

    public void displaySelectedScreen(int id) {
        switch (id){
            case R.id.menu1:
                finish();
                break;
            case R.id.menu2:
                nextSelect.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(4));
                startActivity(nextSelect);
                finish();
                break;
            case R.id.menu3:
                nextSelect.putExtra(Category.class.getCanonicalName(), Maltabu.categories.get(2));
                startActivity(nextSelect);
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }

    public void GetCategories()
    {
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
            Region regionRegion = new Region(categoryObject.getString("value"), categoryObject.getString("name"), categoryObject.getString("firstCase"));
            ArrayList<City> cityArrayList = new ArrayList<>();
            JSONArray cities = categoryObject.getJSONArray("cities");
            ArrayList jsonObjList2 = googleJson.fromJson(String.valueOf(cities), ArrayList.class);
            for(int j=0; j<jsonObjList2.size(); j++) {
                JSONObject cityObject = cities.getJSONObject(j);
                City city = new City(cityObject.getString("value"), cityObject.getString("name"), cityObject.getString("firstCase"));
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
            Category category = new Category(regionObject.getString("value"), regionObject.getString("name"), regionObject.getString("firstCase"),count);
            ArrayList<Catalog> catalogsArrayList = new ArrayList<>();
            JSONArray catalogs = regionObject.getJSONArray("catalogs");
            ArrayList jsonObjList3 = googleJson.fromJson(String.valueOf(catalogs), ArrayList.class);
            for (int l=0; l<jsonObjList3.size();l++)
            {
                JSONObject catalogObj = catalogs.getJSONObject(l);
                int count2 = catalogObj.getJSONObject("stat").getInt("count");
                Catalog catalog = new Catalog(catalogObj.getString("value"), catalogObj.getString("name"), catalogObj.getString("firstCase"),count2);
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

    public void GetDictionary()
    {
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
                        epicDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
