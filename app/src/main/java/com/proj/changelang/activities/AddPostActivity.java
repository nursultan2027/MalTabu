package com.proj.changelang.activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CategoryAdapter;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Region;

import java.util.ArrayList;

public class AddPostActivity extends AppCompatActivity{
//    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        LinearLayout cl1 = (LinearLayout) findViewById(R.id.slectedRegion);
        for (int i=0; i<Maltabu.categories.size();i++){
            Category cat = Maltabu.categories.get(i);
            ArrayList<Catalog> catalogs = cat.catalogs;
            ArrayList<String> arr = new ArrayList<>();
            arr.add(cat.getName());
                for (int j=0; j<catalogs.size();j++){
                    arr.add(catalogs.get(j).getName());
                }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
            final Spinner spinner = new Spinner(this);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(AddPostActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPostActivity.this,AddPostActivity2.class));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            cl1.addView(spinner);
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

}
