package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.ViewPagerAdapter;
import kz.maltabu.app.maltabukz.fragments.MyPostsFragment;
import kz.maltabu.app.maltabukz.fragments.MyProfileFragment;
import kz.maltabu.app.maltabukz.fragments.MyScoreFragment;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

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
                new CustomAnimator().animateViewBound(arr);
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
