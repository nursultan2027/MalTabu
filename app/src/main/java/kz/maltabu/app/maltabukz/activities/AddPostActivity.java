package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Catalog;
import kz.maltabu.app.maltabukz.models.Category;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AddPostActivity extends AppCompatActivity{
    private ArrayList<Spinner> spinners = new ArrayList<>();
    private boolean [] balls = new boolean[7];
    private FileHelper fileHelper;
    private Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7;
    private ImageView txt;
    private TextView title, chooseCatalog;
    private Dialog epicDialog;
    private JSONObject dict;
    private ArrayList<Category> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_new);
        fileHelper = new FileHelper(this);
        txt = (ImageView) findViewById(R.id.arrr);
        epicDialog = new Dialog(this);
        title = (TextView) findViewById(R.id.textView);
        String lang = (String) Paper.book().read("language");
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        chooseCatalog = (TextView) findViewById(R.id.chooseCatalogCategory);
        try {
            getCategories();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Resources resources = context.getResources();
        title.setText(resources.getString(R.string.addPost));
        chooseCatalog.setText(resources.getString(R.string.chooseCategoryCatalog));
        spinners.add(spinner1);
        spinners.add(spinner2);
        spinners.add(spinner3);
        spinners.add(spinner4);
        spinners.add(spinner5);
        spinners.add(spinner6);
        spinners.add(spinner7);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final  View vv = new View(this);
        try {
            for (int i=0; i<categories.size();i++){
            Category cat = categories.get(i);

            balls[i] = false;
            ArrayList<Catalog> catalogs = cat.catalogs;
            ArrayList<String> arrCatalogs = new ArrayList<>();
            ArrayList<String> arrCategories = new ArrayList<>();
            if(Maltabu.lang.equals("ru")) {
                arrCategories.add(cat.getName());
                spinners.get(i).setPrompt(cat.getName());
            }
            else {
                String kazName = dict.getString(cat.getName());
                if(kazName.toLowerCase().equals("жем")){
                    kazName = "Жем-шөп";
                }
                arrCategories.add(kazName);
                spinners.get(i).setPrompt(kazName);
            }
            for (int j=0; j<catalogs.size();j++) {
                if (Maltabu.lang.equals("ru")) {
                    String tabName = catalogs.get(j).getName();
                    if (tabName.toLowerCase().equals("бараны")) {
                        tabName = "Овцы";
                    }
                    arrCatalogs.add(tabName);
                }
                else
                    arrCatalogs.add(dict.getString(catalogs.get(j).getName()));
            }
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrCategories);
            spinners.get(i).setAdapter(adapter2);
            final int finalI = i;
            spinners.get(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(balls[finalI]) {
                        sDialog();
                        SecondThread thread = new SecondThread(finalI, position);
                        thread.start();
                    }
                    balls[finalI]=true;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Go(int position, int finalI){
        Intent intent2 = new Intent(AddPostActivity.this,AddPostActivity2.class);
        Catalog catalog = null;
        catalog = categories.get(finalI).catalogs.get(position-1);
        intent2.putExtra("catalog", catalog);
        startActivity(intent2);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

    private void getCategories() throws JSONException {
        dict = fileHelper.diction();
        for (int i=0; i<fileHelper.getCategoriesFromFile().size();i++) {
            Category category = fileHelper.getCategoriesFromFile().get(i);
            categories.add(category);
        }
    }

    public class SecondThread extends Thread{
        int i, p;
        SecondThread(int i, int p){
            this.i = i;
            this.p = p;
        }

        @Override
        public void run() {
            Go(p,i);
        }
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
