package com.proj.changelang.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CategoryAdapter;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Region;

import org.json.JSONException;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AddPostActivity extends AppCompatActivity{
    private ArrayList<Spinner> spinners = new ArrayList<>();
    private boolean [] balls = new boolean[7];
    private FileHelper fileHelper;
    private ImageView txt;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        txt = (ImageView) findViewById(R.id.arrr);
        title = (TextView) findViewById(R.id.textView);
        String lang = (String) Paper.book().read("language");
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        title.setText(resources.getString(R.string.addPost));

        spinners = new ArrayList<>();
        fileHelper = new FileHelper(this);
        LinearLayout cl1 = (LinearLayout) findViewById(R.id.slectedRegion);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try {
            for (int i=0; i<fileHelper.getCategoriesFromFile().size();i++){
            Category cat = fileHelper.getCategoriesFromFile().get(i);

            balls[i] = false;
            ArrayList<Catalog> catalogs = cat.catalogs;
            ArrayList<String> arr = new ArrayList<>();
            if(Maltabu.lang.equals("ru"))
                arr.add(cat.getName());
            else
                arr.add(fileHelper.diction().getString(cat.getName()));
            for (int j=0; j<catalogs.size();j++) {
                if (Maltabu.lang.equals("ru"))
                    arr.add(catalogs.get(j).getName());
                else
                    arr.add(fileHelper.diction().getString(catalogs.get(j).getName()));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
            final Spinner spinner = new Spinner(this);
            spinners.add(spinner);
            spinners.get(i).setAdapter(adapter);
            final int finalI = i;
                spinners.get(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(balls[finalI]) {
                        try {
                            Intent intent2 = new Intent(AddPostActivity.this,AddPostActivity2.class);
                            Catalog catalog = null;
                                catalog = fileHelper.getCategoriesFromFile().get(finalI).catalogs.get(position-1);
                            intent2.putExtra("catalog", catalog);
                            startActivity(intent2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    balls[finalI]=true;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                cl1.addView(spinner);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

}
