package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.fragments.MyPostsFragment;
import com.proj.changelang.fragments.MyProfileFragment;
import com.proj.changelang.fragments.MyScoreFragment;
import com.proj.changelang.helpers.FileHelper;

public class AddScore extends AppCompatActivity{

    private ImageView arr;
    private TextView numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        numb = (TextView) findViewById(R.id.textView44);
        arr = (ImageView) findViewById(R.id.arr);
        numb.setText(numb.getText().toString()+getIntent().getStringExtra("numb"));
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
