package com.proj.changelang.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.ImageFragment;
import com.proj.changelang.models.ImageFragment2;
import com.proj.changelang.models.Post;

public class ShowDetailsImages extends AppCompatActivity {
    private TextView title, content, price, phone, location, date, photos;
    private ImageView img;
    private Post post;
    private int PAGE_COUNT, selectedImg;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post = getIntent().getParcelableExtra("post");
        selectedImg = getIntent().getIntExtra("select",0);
        PAGE_COUNT = post.getImages().size();
        setContentView(R.layout.image_dialog);
        ViewPager asd = (ViewPager) findViewById(R.id.pages);
        final TextView txt = (TextView) findViewById(R.id.photos);
        if(post.getImages().size()>0)
            txt.setText(String.valueOf(selectedImg+1+"/"+post.getImages().size()));
        pagerAdapter = new MyFragmentPagerAdapter2(getSupportFragmentManager());
        asd.setAdapter(pagerAdapter);
        asd.setCurrentItem(selectedImg);
        asd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(post.getImages().size()>0)
                    txt.setText(String.valueOf(position+1+"/"+post.getImages().size()));
            }
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private class MyFragmentPagerAdapter2 extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment2.newInstance(position, post.getImages().get(position).getMedium());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

}
