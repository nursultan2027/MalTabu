package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maltabu.app.R;
import kz.maltabu.app.maltabukz.adapters.RegionAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.models.Region;

import org.json.JSONException;

import java.util.ArrayList;

public class SecondSelect1 extends AppCompatActivity{

    private TextView textView;
    private FileHelper fileHelper;
    private NavigationView view;
    private Button okok;
    private ArrayList<Region> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        fileHelper = new FileHelper(this);
        RegionAdapter adapter = null;
        try {
            categories = new ArrayList<>();
            categories.add(new Region(getResources().getString(R.string.Option2)));
            categories.addAll(fileHelper.getRegionsFromFile());
            adapter = new RegionAdapter(this, R.layout.category_item, categories);
            LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.categories);
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = adapter.getView(i, null, listViewReplacement);
                listViewReplacement.addView(view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}
