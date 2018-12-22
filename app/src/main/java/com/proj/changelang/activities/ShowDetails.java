package com.proj.changelang.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.fragments.ImageFragment;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowDetails extends AppCompatActivity {
    private TextView title, content, price, phone, location, date, photos;
    private ImageView img;
    private Post post;
    private LinearLayout layout;
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
        phonePlaceholder();
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

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] gg = phonesArr(post.getPhones());
                phone.setText("+7("+gg[0].substring(0,3)+")"+gg[0].substring(3,gg[0].length()));
                if(gg.length>1){
                    for (int j=1; j<gg.length; j++){
                        TextView phone2 = new TextView(ShowDetails.this);
                        phone2.setText("+7("+gg[j].substring(0,3)+")"+gg[j].substring(3,gg[j].length()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            phone2.setTextColor(getColor(R.color.MaltabuBlue));
                        }
                        layout.addView(phone2);
                    }
                }
                showPhone(post.getNumber());
            }
        });
    }

    private void initViews() {
        post = getIntent().getParcelableExtra("post");
        img = (ImageView) findViewById(R.id.finish);
        layout = (LinearLayout) findViewById(R.id.lin);
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

    public void showPhone(String numb){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/posts/"+numb+"/phone")
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
                    Toast.makeText(ShowDetails.this, s1, Toast.LENGTH_LONG).show();
                }
            }
        };
        asyncTask1.execute();
    }

    public String [] phonesArr(String s){
        String [] spl = s.split(";");
        for(int i=0; i<spl.length;i++){
            spl[i].replace(";","");
        }
        return spl;
    }

    public void phonePlaceholder(){
        String [] gg = phonesArr(post.getPhones());
        String placeHplder="+7("+gg[0].substring(0,3);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        Resources resources = context.getResources();
        phone.setText(placeHplder+") "+resources.getString(R.string.showPhone));
    }
}
