package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CategoryAdapter;
import com.proj.changelang.adapters.RegionAdapter;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Category;

public class FirstSelect1 extends AppCompatActivity{

    private TextView textView;
    private NavigationView view;
    private Button okok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.category_item, Maltabu.categories);
        LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.categories);
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listViewReplacement);
            listViewReplacement.addView(view);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity2.class));
        finish();
    }

}
