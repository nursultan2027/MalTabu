package com.proj.changelang.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.fragments.MyPostsFragment;
import com.proj.changelang.fragments.MyProfileFragment;
import com.proj.changelang.fragments.MyScoreFragment;
import com.proj.changelang.helpers.FileHelper;

public class CabinetActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FileHelper fileHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabinet);
        fileHelper = new FileHelper(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MyScoreFragment fragment1 = new MyScoreFragment();
        MyPostsFragment fragment2 = new MyPostsFragment();
        MyProfileFragment fragment3 = new MyProfileFragment();
        adapter.addFragment(fragment2,"Мои объявления");
        adapter.addFragment(fragment3,"Мои профиль");
        adapter.addFragment(fragment1,"Выписки по счету");
        viewPager.setAdapter(adapter);
    }
}
