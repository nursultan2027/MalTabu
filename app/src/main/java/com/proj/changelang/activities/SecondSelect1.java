package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.RegionAdapter;
import com.proj.changelang.adapters.RegionAdapter2;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;

import org.json.JSONException;

public class SecondSelect1 extends AppCompatActivity{

    private TextView textView;
    private FileHelper fileHelper;
    private NavigationView view;
    private Button okok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        fileHelper = new FileHelper(this);
        if (getIntent().getStringExtra("target")!=null){

        if (getIntent().getStringExtra("target").equals("filter")){
            try {
                RegionAdapter2 adapter = new RegionAdapter2(this, R.layout.category_item, fileHelper.getRegionsFromFile());
                LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.categories);
                for (int i = 0; i < adapter.getCount(); i++) {
                    View view = adapter.getView(i, null, listViewReplacement);
                    listViewReplacement.addView(view);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        }
        else {
            RegionAdapter adapter = null;
            try {
                adapter = new RegionAdapter(this, R.layout.category_item, fileHelper.getRegionsFromFile());
                LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.categories);
                for (int i = 0; i < adapter.getCount(); i++) {
                    View view = adapter.getView(i, null, listViewReplacement);
                    listViewReplacement.addView(view);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("target")!=null) {
            startActivity(new Intent(this, FilterActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity2.class));
            finish();
        }
    }
}
