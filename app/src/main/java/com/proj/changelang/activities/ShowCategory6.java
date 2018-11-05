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

public class ShowCategory6 extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Category category;
    private View headerView;
    private Dialog epicDialog;
    private JSONArray resObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_catalog);
        category = getIntent().getParcelableExtra(Category.class.getCanonicalName());
        epicDialog = new Dialog(this);
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
        viewInit();
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
        String [] catalogStr = {"5ab672c9559d5e049c25a64b",
                "5ab672c9559d5e049c25a64c","5ab672c9559d5e049c25a64d",
                "5ab672c9559d5e049c25a64e","5ab672c9559d5e049c25a64f","5ab672c9559d5e049c25a650"};
        bundle1.putString("catalog","");
        bundle1.putBoolean("isCatalog", false);
        CategoryFragment fragobj1=new CategoryFragment();
        fragobj1.setArguments(bundle1);

        if(Maltabu.lang.toLowerCase().equals("ru")) {
            adapter.addFragment(fragobj1, "все");
        } else {
            adapter.addFragment(fragobj1, "барлық");
        }
        for (int i=0; i<category.catalogs.size();i++){
            Bundle bundle2 = new Bundle();
            bundle2.putString("catalog",catalogStr[i]);
            bundle2.putBoolean("isCatalog", true);
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
}
