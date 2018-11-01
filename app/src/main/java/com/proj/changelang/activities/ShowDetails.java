package com.proj.changelang.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.ImageFragment;
import com.proj.changelang.models.Post;

public class ShowDetails extends AppCompatActivity {
    private TextView title, content, price, phone, location, date;
    private ImageView img;
    private Post post;
    private int PAGE_COUNT;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);
        post = getIntent().getParcelableExtra("post");
        initViews();
        setInfo();
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
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

    private void setInfo() {
        title.setText(post.getTitle());
        content.setText(post.getContent());
        price.setText(post.getPrice());
        location.setText(post.getCityID());
        date.setText(post.getCreatedAt());
        PAGE_COUNT = post.getImages().size();
    }


    private void initViews() {
        title = (TextView) findViewById(R.id.textView2);
        content = (TextView) findViewById(R.id.textView777);
        price = (TextView) findViewById(R.id.textView6);
        phone = (TextView) findViewById(R.id.phoneTxt);
        location = (TextView) findViewById(R.id.locationTxt);
        date = (TextView) findViewById(R.id.dateTxt);
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(position, post.getImages().get(position).getMedium());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
}
