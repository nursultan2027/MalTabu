package kz.maltabu.app.maltabukz.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.CategoryAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Category;

import org.json.JSONException;

import java.util.ArrayList;

public class FirstSelect1 extends AppCompatActivity{

    private TextView textView;
    private NavigationView view;
    private Button okok;
    private FileHelper fileHelper;
    private ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        fileHelper = new FileHelper(this);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        CategoryAdapter adapter = null;
        try {
            categories = new ArrayList<>();
            categories.add(new Category(context.getResources().getString(R.string.Option1)));
            categories.addAll(fileHelper.getCategoriesFromFile());
            adapter = new CategoryAdapter(this, R.layout.category_item, categories);
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
        startActivity(new Intent(this,MainActivity2.class));
        finish();
    }

}
