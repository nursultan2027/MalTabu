package kz.maltabu.app.maltabukz.activities;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.fragments.ImageFragment2;
import kz.maltabu.app.maltabukz.models.Post;

public class ShowDetailsImages extends AppCompatActivity {
    private ImageView img;
    private SlidrInterface slidrInterface;
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

        SlidrConfig config = new SlidrConfig.Builder()
                                .position(SlidrPosition.BOTTOM)
                                .sensitivity(1f)
                                .scrimColor(Color.BLACK)
                                .scrimStartAlpha(0.8f)
                                .scrimEndAlpha(0f)
                                .velocityThreshold(2400)
                                .distanceThreshold(0.25f)
                                .edge(true|false)
                                .edgeSize(0.7f) // The % of the screen that counts as the edge, default 18%
                                .listener(new SlidrListener(){
                                    @Override
                                    public void onSlideStateChanged(int state) {

                                    }

                                    @Override
                                    public void onSlideChange(float percent) {

                                    }

                                    @Override
                                    public void onSlideOpened() {

                                    }

                                    @Override
                                    public void onSlideClosed() {

                                    }})
                                .build();

        Slidr.attach(this, config);

        setContentView(R.layout.image_dialog);
        pager = (ViewPager) findViewById(R.id.pages);
        img = (ImageView) findViewById(R.id.arr);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final TextView txt = (TextView) findViewById(R.id.photos);
        if(post.getImages().size()>0)
            txt.setText(String.valueOf(selectedImg+1+"/"+post.getImages().size()));
        pagerAdapter = new MyFragmentPagerAdapter2(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(selectedImg);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            if(post.getImages().get(0).getMedium().contains("http"))
                return ImageFragment2.newInstance(position, post.getImages().get(position).getBig());
            else
                return ImageFragment2.newInstance(position, "https://maltabu.kz/"+post.getImages().get(position).getBig());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

}
