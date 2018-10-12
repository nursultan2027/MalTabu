package com.proj.changelang.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.CategoryFragment;

import org.json.JSONException;

import java.util.zip.Inflater;

import io.paperdb.Paper;

public class ShowCategory extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Category category;
    private View headerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_catalog);
        category = getIntent().getParcelableExtra(Category.class.getCanonicalName());
        String kazName = null;
        try {
            kazName = Maltabu.jsonObject.getString(category.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Maltabu.lang.toLowerCase().equals("ru")) {
            setTitle(category.getName());
        } else {
            setTitle(kazName);
        }

        headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.tab_item, null, false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


//        TextView txt = (TextView) headerView.findViewById(R.id.text);
//        txt.setText("все");
//        ConstraintLayout con = (ConstraintLayout) headerView.findViewById(R.id.con);
//        tabLayout.getTabAt(0).setCustomView(con);
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(Maltabu.lang.toLowerCase().equals("ru")) {
            adapter.addFragment(new CategoryFragment(), "все");
        } else {
            adapter.addFragment(new CategoryFragment(), "барлық");
        }
        for (int i=0; i<category.catalogs.size();i++){
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                adapter.addFragment(new CategoryFragment(), category.catalogs.get(i).getName());
            } else {
                try {
                    String kazName = Maltabu.jsonObject.getString(category.catalogs.get(i).getName());
                    adapter.addFragment(new CategoryFragment(), kazName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        viewPager.setAdapter(adapter);
    }

}
