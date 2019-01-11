package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CityAdapter;
import com.proj.changelang.models.Region;
import com.proj.changelang.models.City;

import java.util.ArrayList;

public class SecondSelect2 extends AppCompatActivity{
    private ArrayList<City> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
                Region region = getIntent().getParcelableExtra(Region.class.getCanonicalName());
                cities = new ArrayList<>();
                cities.add(new City(region.getName()));
                cities .addAll(region.cities);
                CityAdapter adapter = new CityAdapter(this, R.layout.category_item, cities, region.getId(), region.getName());
                LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.categories);
                for (int i = 0; i < adapter.getCount(); i++) {
                    View view = adapter.getView(i, null, listViewReplacement);
                    listViewReplacement.addView(view);
            }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

}
