package com.proj.changelang.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.proj.changelang.models.Image;
import com.proj.changelang.models.ImageFragment;
import com.proj.changelang.models.Post;

public class ShowDetails extends AppCompatActivity {
    private TextView title, content, price, phone, location, date, photos;
    private ImageView img;
    private Post post;
    private int PAGE_COUNT;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private Dialog epicDialog;
    private GestureDetector tapGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);
        epicDialog = new Dialog(this);
        post = getIntent().getParcelableExtra("post");
        initViews();
        setInfo();
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
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

    private void setInfo() {
        title.setText(post.getTitle());
        content.setText(post.getContent());
        price.setText(post.getPrice());
        location.setText(post.getCityID());
        date.setText(post.getCreatedAt());
        PAGE_COUNT = post.getImages().size();
        if(post.getImages().size()>0)
            photos.setText(String.valueOf("1/"+post.getImages().size()));
    }


    private void initViews() {
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
            sDialog();
            return false;
        }
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.image_dialog);
        ViewPager asd = (ViewPager) epicDialog.findViewById(R.id.pages);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        asd.setAdapter(pagerAdapter);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
