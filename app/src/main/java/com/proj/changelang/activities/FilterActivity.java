package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.CategoryAdapter;
import com.proj.changelang.adapters.PostAdapter;
import com.proj.changelang.adapters.RegionAdapter;
import com.proj.changelang.adapters.RegionAdapter2;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.City;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Region;

import java.nio.channels.CancelledKeyException;

public class FilterActivity extends AppCompatActivity{
    private ImageView img;
    private Button btn1, btn2, btn3, btn4;
    private EditText ed1, ed2;
    private CheckBox photo, barter, bargain;
    private Intent regIntent, regIntent2;
    private FilterModel filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiter);
        img = (ImageView) findViewById(R.id.arrr);
        btn1 = (Button) findViewById(R.id.select11);
        btn2 = (Button) findViewById(R.id.select12);
        ed1 = (EditText) findViewById(R.id.editText7);
        ed2 = (EditText) findViewById(R.id.editText8);
        btn3 = (Button) findViewById(R.id.select21);
        btn4 = (Button) findViewById(R.id.select22);
        photo = (CheckBox) findViewById(R.id.checkBox2);
        barter = (CheckBox) findViewById(R.id.checkBox3);
        bargain = (CheckBox) findViewById(R.id.checkBox);
        regIntent = new Intent(this, SecondSelect1.class);
        regIntent2 = new Intent(this, SecondSelect2.class);
        if (Maltabu.RegionFilter!=null) {
            Region reg = getIntent().getParcelableExtra(Region.class.getCanonicalName());
            btn1.setText(Maltabu.RegionFilter);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filter = new FilterModel();
                    if (Maltabu.cityId!=null)
                        filter.setCityId(Maltabu.cityId);
                    if (Maltabu.regId!=null)
                        filter.setRegId(Maltabu.regId);
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
                    regIntent2.putExtra("target", "filter");
                    startActivity(regIntent2);
                    finish();
                }
            });
        }
        if (Maltabu.CityFilter!=null) {
            btn2.setText(Maltabu.CityFilter);
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter = new FilterModel();
                if (Maltabu.cityId!=null)
                    filter.setCityId(Maltabu.cityId);
                if (Maltabu.regId!=null)
                    filter.setRegId(Maltabu.regId);
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
                regIntent.putExtra("target", "filter");
                startActivity(regIntent);
                finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, MainActivity.class));
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

    public void ClearFilter(){
        Maltabu.Region = null;
        Maltabu.cityId = null;
        Maltabu.regId = null;
        Maltabu.CityFilter = null;
        Maltabu.RegionFilter = null;
        btn1.setText(getResources().getString(R.string.chooseRegion));
        btn2.setText(getResources().getString(R.string.chooseCity));
        ed1.setText("");
        ed2.setText("");
        photo.setChecked(false);
        barter.setChecked(false);
        bargain.setChecked(false);
        Maltabu.filterModel = null;
    }

    public void PostFilter(){
        filter = new FilterModel();
        if (Maltabu.cityId!=null)
            filter.setCityId(Maltabu.cityId);
        if (Maltabu.regId!=null)
            filter.setRegId(Maltabu.regId);
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
