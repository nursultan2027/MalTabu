package com.proj.changelang.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.Toolbar;

import com.proj.changelang.R;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.ImageFragment;
import com.proj.changelang.models.ImageFragment2;
import com.proj.changelang.models.Post;

import org.json.JSONException;

public class ShowDetails extends AppCompatActivity {
    private TextView title, content, price, phone, location, date, photos;
    private ImageView img;
    private Post post;
    private int PAGE_COUNT;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private int selectedImg;
    private Intent imagesIntent;
    private GestureDetector tapGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);
        imagesIntent = new Intent(this, ShowDetailsImages.class);
        initViews();
        setInfo();
    }

    private void setInfo() {
        title.setText(post.getTitle());
        content.setText(post.getContent());
        price.setText(post.getPrice());
        location.setText(post.getCityID());
        String dates [] = post.getCreatedAt().split(",");
        if (Maltabu.lang.equals("ru"))
            date.setText(dates[0]+ " "+dates[1]);
        else
            date.setText(dates[0]+ " "+dates[2]);
        PAGE_COUNT = post.getImages().size();
        if(post.getImages().size()>0)
            photos.setText(String.valueOf("1/"+post.getImages().size()));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        tapGestureDetector = new GestureDetector(this, new TapGestureListener());
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                selectedImg = position;
                if(post.getImages().size()>0)
                    photos.setText(String.valueOf(position+1+"/"+post.getImages().size()));
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

    private void initViews() {
        post = getIntent().getParcelableExtra("post");
        img = (ImageView) findViewById(R.id.finish);
        title = (TextView) findViewById(R.id.textView2);
        content = (TextView) findViewById(R.id.textView777);
        price = (TextView) findViewById(R.id.textView6);
        phone = (TextView) findViewById(R.id.phoneTxt);
        location = (TextView) findViewById(R.id.locationTxt);
        date = (TextView) findViewById(R.id.dateTxt);
        photos = (TextView) findViewById(R.id.photos);
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

    class TapGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            imagesIntent.putExtra("post", post);
            imagesIntent.putExtra("pageCount", PAGE_COUNT);
            imagesIntent.putExtra("select", selectedImg);
            startActivity(imagesIntent);
            return false;
        }
    }
}
