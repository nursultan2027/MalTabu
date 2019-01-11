package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CatalogAdapter;
import com.proj.changelang.adapters.CityAdapter;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;
import com.proj.changelang.models.Region;

import java.util.ArrayList;

public class FirstSelect2 extends AppCompatActivity{
    private ArrayList<Catalog> catalogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        Category region = getIntent().getParcelableExtra(Category.class.getCanonicalName());
        catalogs = new ArrayList<>();
        catalogs.add(new Catalog(region.getName()));
        catalogs.addAll(region.catalogs);
        CatalogAdapter adapter = new CatalogAdapter(this, R.layout.category_item, catalogs, region.getId(), region.getName());
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
