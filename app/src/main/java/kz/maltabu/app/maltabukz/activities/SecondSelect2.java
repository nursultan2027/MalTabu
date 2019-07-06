package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.CityAdapter;
import kz.maltabu.app.maltabukz.models.Region;
import kz.maltabu.app.maltabukz.models.City;

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
