package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.fragments.MyPostsFragment;
import com.proj.changelang.fragments.MyProfileFragment;
import com.proj.changelang.fragments.MyScoreFragment;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;

import org.json.JSONException;
import org.json.JSONObject;

public class CabinetActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView arr;
    private FileHelper fileHelper;
    private JSONObject object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabinet);
        fileHelper = new FileHelper(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        arr = (ImageView) findViewById(R.id.arr);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.posts);
//        tabLayout.getTabAt(1).setIcon(R.drawable.cabinettab2);
//        tabLayout.getTabAt(2).setIcon(R.drawable.bill);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MyScoreFragment fragment1 = new MyScoreFragment();
        MyPostsFragment fragment2 = new MyPostsFragment();
        MyProfileFragment fragment3 = new MyProfileFragment();
        if(Maltabu.lang.equals("ru")) {
            adapter.addFragment(fragment2, "Мои объявления");
            adapter.addFragment(fragment3, "Мои профиль");
            adapter.addFragment(fragment1, "Выписки по счету");
        } else {
            try {
                adapter.addFragment(fragment2,object.getString("Мои объявления"));
                adapter.addFragment(fragment3, object.getString("Мой профиль"));
                adapter.addFragment(fragment1, object.getString("Выписка по счету"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}
