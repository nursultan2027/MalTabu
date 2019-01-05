package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Region;

import org.json.JSONException;

import java.util.ArrayList;

import io.paperdb.Paper;

public class FilterActivity extends AppCompatActivity{
    private ImageView img;
    private int pos1 = 101, pos2 = 101;
    private Button btn3, btn4;
    private EditText ed1, ed2;
    private Dialog epicDialog;
    private Spinner regSpin, citySpin;
    private TextView [] texts = new TextView[7];
    private CheckBox photo, barter, bargain;
    private FilterModel filter;
    private FileHelper fileHelper;
    private String CityID, RegionID;
    private ArrayList<Region> regions = new ArrayList();
    private ArrayList<ArrayList<String>> citiesArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiter);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
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
        updateView(Maltabu.lang);
        getCities();
        ArrayList<String> arr = new ArrayList<>();
        arr.add(LocaleHelper.setLocale(this, Maltabu.lang).getResources().getString(R.string.chooseRegion));
        for (int i=0; i<regions.size();i++) {
            Region region = regions.get(i);
            arr.add(region.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        regSpin.setAdapter(adapter);
        regSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    if(position>0){
                        RegionID = regions.get(position-1).getId();
                        pos1=position;
                        if(Maltabu.filterModel!=null) {
                            if (Maltabu.filterModel.getCityId() != null) {
                                if(pos2==102)
                                    Maltabu.filterModel.setCityPosition(0);
                            }
                        }
                        pos2 = 102;
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_dropdown_item, citiesArr.get(position-1));
                        citySpin.setAdapter(adapter2);
                    }
                    else {
                        RegionID=null;
                        ArrayList<String> asd = new ArrayList<>();
                        asd.add(LocaleHelper.setLocale(FilterActivity.this, Maltabu.lang).getResources().getString(R.string.chooseCity));
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FilterActivity.this, android.R.layout.simple_spinner_dropdown_item,asd);
                        citySpin.setAdapter(adapter2);
                    }

                citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if(position>0) {
                            if (pos > 0) {
                                pos2 = pos;
                                CityID = regions.get(position - 1).cities.get(pos - 1).getId();
                                Toast.makeText(FilterActivity.this,
                                        regions.get(position - 1).cities.get(pos - 1).getName(), Toast.LENGTH_LONG).show();
                                }
                        } else {
                            CityID = null;
                        }
                        epicDialog.dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if(Maltabu.filterModel!=null) {
                    if (Maltabu.filterModel.getCityId() != null) {
                        citySpin.setSelection(Maltabu.filterModel.getCityPosition());
                    }
                }
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
                if (isConnected()) {
                    PostThread thread2 = new PostThread();
                    thread2.start();
                }
                else {
                    Toast.makeText(FilterActivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                }
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
            if(Maltabu.filterModel.getRegId()!=null){
                regSpin.setSelection(Maltabu.filterModel.getRegPosition());
            }
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
        regSpin.setSelection(0);
        citySpin.setSelection(0);
        RegionID = null;
        CityID = null;
        ed1.setText("");
        ed2.setText("");
        photo.setChecked(false);
        barter.setChecked(false);
        bargain.setChecked(false);
        Maltabu.filterModel = null;
        updateView(Maltabu.lang);
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    public void PostFilter(){
        filter = new FilterModel();
        int filterCount = 0;
        if (CityID!=null) {
            filter.setCityId(CityID);
            filter.setCityPosition(pos2);
            filterCount++;
        }
        if (RegionID!=null) {
            filter.setRegPosition(pos1);
            filter.setRegId(RegionID);
            filterCount++;
        }
        if (photo.isChecked())
        {
            filter.setWithPhoto(true);
            filterCount++;
        }
        if (barter.isChecked())
        {
            filter.setBarter(true);
            filterCount++;
        }
        if (bargain.isChecked())
        {
            filter.setBargain(true);
            filterCount++;
        }
        if (!ed1.getText().toString().isEmpty())
        {
            filter.setPrice1(ed1.getText().toString());
            filterCount++;
        }
        if (!ed2.getText().toString().isEmpty())
        {
            filter.setPrice2(ed2.getText().toString());
            filterCount++;
        }
        if (filterCount>0) {
            Maltabu.filterModel = filter;
        }
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

    public void getCities(){
        try {
            for (int i=0; i<fileHelper.getRegionsFromFile().size();i++) {
                Region region = fileHelper.getRegionsFromFile().get(i);
                regions.add(region);
                ArrayList<String> cityarr = new ArrayList<>();
                cityarr.add(LocaleHelper.setLocale(this, Maltabu.lang).getResources().getString(R.string.chooseCity));
                for (int j=0; j<region.cities.size(); j++){
                    cityarr.add(region.cities.get(j).getName());
                }
                citiesArr.add(cityarr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }

    public class PostThread extends Thread{
        PostThread(){}

        @Override
        public void run() {
            PostFilter();
        }
    }
}
