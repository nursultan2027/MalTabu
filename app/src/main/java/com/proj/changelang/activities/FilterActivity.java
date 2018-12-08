package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CategoryAdapter;
import com.proj.changelang.adapters.PostAdapter;
import com.proj.changelang.adapters.RegionAdapter;
import com.proj.changelang.adapters.RegionAdapter2;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.City;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Region;

import org.json.JSONException;

import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;

import io.paperdb.Paper;

public class FilterActivity extends AppCompatActivity{
    private ImageView img;
    private Button btn3, btn4;
    private EditText ed1, ed2;
    private Spinner regSpin, citySpin;
    private TextView [] texts = new TextView[7];
    private CheckBox photo, barter, bargain;
    private FilterModel filter;
    private FileHelper fileHelper;
    private String CityID, RegionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiter);
        fileHelper = new FileHelper(this);
        texts[0] = findViewById(R.id.titlet);
        texts[1] = findViewById(R.id.textView7);
        texts[2] = findViewById(R.id.textView12);
        texts[3] = findViewById(R.id.textView13);
        texts[4] = findViewById(R.id.textView8);
        texts[5] = findViewById(R.id.textView14);
        texts[6] = findViewById(R.id.texxx);
        regSpin = (Spinner) findViewById(R.id.regSpin);
        citySpin = (Spinner) findViewById(R.id.citySpin);
        img = (ImageView) findViewById(R.id.arrr);
        ed1 = (EditText) findViewById(R.id.editText7);
        ed2 = (EditText) findViewById(R.id.editText8);
        btn3 = (Button) findViewById(R.id.select21);
        btn4 = (Button) findViewById(R.id.select22);
        photo = (CheckBox) findViewById(R.id.checkBox2);
        barter = (CheckBox) findViewById(R.id.checkBox3);
        bargain = (CheckBox) findViewById(R.id.checkBox);
        updateView((String) Paper.book().read("language"));

        ArrayList<String> arr = new ArrayList<>();
        arr.add(getResources().getString(R.string.chooseRegion));
        try {
            for (int i=0; i<fileHelper.getRegionsFromFile().size();i++) {
                Region region = fileHelper.getRegionsFromFile().get(i);
                arr.add(region.getName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        regSpin.setAdapter(adapter);
        regSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                try {
                    if(position>0){
                        RegionID = fileHelper.getRegionsFromFile().get(position-1).getId();
                    }
                    else {
                        RegionID=null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<String> arr = new ArrayList<>();
                arr.add(getResources().getString(R.string.chooseCity));
                try {
                    if(position>0) {
                        for (int i = 0; i < fileHelper.getRegionsFromFile().get(position-1).cities.size(); i++) {
                            City city = fileHelper.getRegionsFromFile().get(position-1).cities.get(i);
                            arr.add(city.getName());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_dropdown_item, arr);
                citySpin.setAdapter(adapter2);

                citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        try {
                            if(position>0) {
                                if (pos > 0) {
                                    CityID = fileHelper.getRegionsFromFile().get(position - 1).cities.get(pos - 1).getId();
                                    Toast.makeText(FilterActivity.this,
                                            fileHelper.getRegionsFromFile().get(position - 1).cities.get(pos - 1).getName(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                CityID = null;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, MainActivity2.class));
                finish();
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFilter();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFilter();
            }
        });
        if (Maltabu.filterModel!=null){
            if(Maltabu.filterModel.getPrice1()!=null){
                ed1.setText(Maltabu.filterModel.getPrice1());
            }
            if(Maltabu.filterModel.getPrice2()!=null){
                ed2.setText(Maltabu.filterModel.getPrice2());
            }
            photo.setChecked(Maltabu.filterModel.isWithPhoto());
            bargain.setChecked(Maltabu.filterModel.isBargain());
            barter.setChecked(Maltabu.filterModel.isBarter());
        }
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        int [] res = {R.string.addPost, R.string.region, R.string.city, R.string.price,
                R.string.onlyPhoto, R.string.onlyTrade, R.string.bargain};
        int [] btnRes = {R.string.chooseRegion, R.string.chooseCity,
                R.string.filterResult, R.string.filterClear};
        for(int i=0; i<7; i++){
            texts[i].setText(resources.getString(res[i]));
        }

        btn3.setText(resources.getString(btnRes[2]));
        btn4.setText(resources.getString(btnRes[3]));
    }

    public void ClearFilter(){
        RegionID = null;
        CityID = null;
        ed1.setText("");
        ed2.setText("");
        photo.setChecked(false);
        barter.setChecked(false);
        bargain.setChecked(false);
        Maltabu.filterModel = null;
        updateView((String) Paper.book().read("language"));
    }

    public void PostFilter(){
        filter = new FilterModel();
        if (CityID!=null)
            filter.setCityId(CityID);
        if (RegionID!=null)
            filter.setRegId(RegionID);
        if (photo.isChecked())
            filter.setWithPhoto(true);
        if (barter.isChecked())
            filter.setBarter(true);
        if (bargain.isChecked())
            filter.setBargain(true);
        if (!ed1.getText().toString().isEmpty())
            filter.setPrice1(ed1.getText().toString());
        if (!ed2.getText().toString().isEmpty())
            filter.setPrice2(ed2.getText().toString());
        Maltabu.filterModel = filter;
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

}
