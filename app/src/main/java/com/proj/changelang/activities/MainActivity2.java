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
import android.support.constraint.ConstraintLayout;
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
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.CatalogFragment;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;
import com.proj.changelang.models.HotFragment;
import com.proj.changelang.models.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView menu1, menu2,menu3,menu4,menu6,menu5,menu7,menu8, menu82, cab;
    private ConstraintLayout cl1, m1, m2, m3, m4, m5, m6, m7;
    private ImageView filter;
    private FileHelper fileHelper;
    private DrawerLayout drawer;
    private Intent filterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetActivityView();
        fileHelper = new FileHelper(this);
        try {
            opentCurrentFragment(Maltabu.fragmentNumb);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetActivityView(){
        setContentView(R.layout.activity_main);
        filter = (ImageView) findViewById(R.id.filter);
        filterIntent = new Intent(this, FilterActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.appBarLayout2);
        setSupportActionBar(toolbar);
        appBar.setOutlineProvider(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Paper.init(this);
        Maltabu.lang = Paper.book().read("language");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        m1  = (ConstraintLayout) view.findViewById(R.id.constraintLayout2);
        m2  = (ConstraintLayout) view.findViewById(R.id.constraintLayout3);
        m3  = (ConstraintLayout) view.findViewById(R.id.constraintLayout4);
        m4  = (ConstraintLayout) view.findViewById(R.id.constraintLayout5);
        m5  = (ConstraintLayout) view.findViewById(R.id.constraintLayout6);
        m6  = (ConstraintLayout) view.findViewById(R.id.constraintLayout9);
        m7  = (ConstraintLayout) view.findViewById(R.id.constraintLayout10);

        menu8  = (TextView) findViewById(R.id.menu8);
        menu82  = (TextView) findViewById(R.id.menu82);
        cl1 = (ConstraintLayout) findViewById(R.id.constraintLayout7);
//        editText = (EditText) findViewById(R.id.search);

        initListeners();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        updateView((String) Paper.book().read("language"));
    }
    private void initListeners() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(filterIntent);
                finish();
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment1();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment2();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment3();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment4();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment5();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment6();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment7();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, AddPostActivity.class));
                finish();
            }
        });

    }
    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        setTitle("Малтабу");
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
//        editText.setHint(resources.getString(R.string.Search));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ru) {
            Paper.book().write("language", "ru");
            Maltabu.lang = "ru";
            updateView((String)Paper.book().read("language"));
            try {
                opentCurrentFragment(Maltabu.fragmentNumb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (item.getItemId() == R.id.menu_kz){
            Paper.book().write("language", "kk");
            Maltabu.lang = "kk";
            updateView((String)Paper.book().read("language"));
            try {
                opentCurrentFragment(Maltabu.fragmentNumb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return true;
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"ru"));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

//        displaySelectedScreen(id);

        return true;
    }

    private String CutString(String str){
        return str.substring(0, 11)+"...";
    }

    private void fragmentMain(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HotFragment fragment = new HotFragment();
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 0;
    }
    private void fragment1() throws JSONException {
        filter.setVisibility(View.GONE);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(1));
        String [] asd = {"5ab672c9559d5e049c25a62c",
                "5ab672c9559d5e049c25a62d","5ab672c9559d5e049c25a62e",
                "5ab672c9559d5e049c25a62f","5ab672c9559d5e049c25a630",
                "5ab672c9559d5e049c25a631", "5ab672c9559d5e049c25a632"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a62b");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 1;
    }
    private void fragment2() throws JSONException { android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(4));
        String [] asd = {"5ab672c9559d5e049c25a645","5ab672c9559d5e049c25a646",
                "5ab672c9559d5e049c25a647","5ab672c9559d5e049c25a648",
                "5ab672c9559d5e049c25a649","5ab672c9559d5e049c25a64a"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a644");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 2;}
    private void fragment3() throws JSONException { android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(2));
        String [] asd = {"5ab672c9559d5e049c25a634","5ab672c9559d5e049c25a635",
                "5ab672c9559d5e049c25a636","5ab672c9559d5e049c25a637","5ab672c9559d5e049c25a638"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a633");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 3;}
    private void fragment4() throws JSONException {  android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(0));
        String [] asd = {"5ab672c9559d5e049c25a63a", "5ab672c9559d5e049c25a63b"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a639");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 4
        ;}
    private void fragment5() throws JSONException {   android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(3));
        String [] asd = {"5ab672c9559d5e049c25a63f","5ab672c9559d5e049c25a640",
                "5ab672c9559d5e049c25a641","5ab672c9559d5e049c25a642",
                "5b0bffe2530c6256285a19b1","5b0bffe2530c6256285a1933","5ab672c9559d5e049c25a643"
        };
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a63e");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 5;}
    private void fragment6() throws JSONException {
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();

        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(5));
        String [] asd =  {"5ab672c9559d5e049c25a64c","5ab672c9559d5e049c25a64d",
                "5ab672c9559d5e049c25a64e","5ab672c9559d5e049c25a64f",
                "5ab672c9559d5e049c25a650"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a64b");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 6;
    }
    private void fragment7() throws JSONException {
        filter.setVisibility(View.VISIBLE);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(6));
        String [] asd = {"5afeb741d151e32d5cc245c4","5afeb741d151e32d5cc245c5"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5afeb741d151e32d5cc245c3");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 7;
    }
    private void opentCurrentFragment(int numb) throws JSONException {
        switch (numb){
            case 0:
                fragmentMain();
                break;
            case 1:
                fragment1();
                break;
            case 2:
                fragment2();
                break;
            case 3:
                fragment3();
                break;
            case 4:
                fragment4();
                break;
            case 5:
                fragment5();
                break;
            case 6:
                fragment6();
                break;
            case 7:
                fragment7();
                break;
        }
    }
}
