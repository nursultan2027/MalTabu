package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.CategoryAddPostAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Catalog;
import kz.maltabu.app.maltabukz.models.Category;

public class AddPostActivity extends AppCompatActivity implements ActivityFragment{
    private ImageView txt;
    private TextView title, chooseCatalog;
    private FileHelper fileHelper;
    private Dialog epicDialog;
    private JSONObject dict;
    private ImageView backButton;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<ConstraintLayout> categoriesLayouts = new ArrayList<>();
    private ArrayList<TextView> categoriesTexts = new ArrayList<>();
    private ArrayList<ExpandableLayout> categoriesExpandLay = new ArrayList<>();
    private ArrayList<RecyclerView> categoriesRecyclers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_list);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
        setViewNames();
        setInfo();
        setListeners();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

    private void getCategories(){
        try {
            dict = fileHelper.diction();
            categories.addAll(fileHelper.getCategoriesFromFile());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void setViewNames(){
        txt = (ImageView) findViewById(R.id.arrr);
        title = (TextView) findViewById(R.id.textView);
        chooseCatalog = (TextView) findViewById(R.id.chooseCatalogCategory);
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs1));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs2));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs3));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs4));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs5));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs6));
        categoriesRecyclers.add((RecyclerView) findViewById(R.id.catalogs7));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable1));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable2));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable3));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable4));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable5));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable6));
        categoriesExpandLay.add((ExpandableLayout)findViewById(R.id.expandable7));
        categoriesTexts.add((TextView)findViewById(R.id.category_1_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_2_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_3_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_4_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_5_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_6_text));
        categoriesTexts.add((TextView)findViewById(R.id.category_7_text));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_1_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_2_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_3_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_4_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_5_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_6_lay));
        categoriesLayouts.add((ConstraintLayout)findViewById(R.id.category_7_lay));
        getCategories();
    }

    private void updateViewByLang(){
        String lang = (String) Paper.book().read("language");
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        title.setText(resources.getString(R.string.addPost));
        chooseCatalog.setText(resources.getString(R.string.chooseCategoryCatalog));
    }

    private void setListeners(){
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        for(int i=0; i<categories.size();i++){
            final int finalI = i;
            categoriesLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(categoriesExpandLay.get(finalI).isExpanded()){
                        categoriesExpandLay.get(finalI).collapse();
                    } else {
                        categoriesExpandLay.get(finalI).expand();
                    }
                }
            });
        }
    }

    private void setInfo(){
        updateViewByLang();
        CategoryAddPostAdapter adapter;
        for(int i=0; i<categories.size();i++){
            if(categories.get(i).getName().equals("Домашние питомцы")){
                categories.get(i).catalogs=removeBirdsCatalog(categories.get(i).catalogs);
            }
            if(Maltabu.lang.equals("ru")) {
                categoriesTexts.get(i).setText(categories.get(i).getName());
            }
            else {
                categoriesTexts.get(i).setText(getKazName(categories.get(i).getName()));
            }
            adapter = new CategoryAddPostAdapter(categories.get(i).catalogs,this, this);
            categoriesRecyclers.get(i).setAdapter(adapter);
        }
    }

    private String getKazName(String rusName){
        try {
            return dict.getString(rusName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void openCurrentFragment() {
        sDialog();
    }

    private ArrayList<Catalog> removeBirdsCatalog(ArrayList<Catalog> catalogs){
        ArrayList<Catalog> result = catalogs;
        for(int i=0; i<catalogs.size();i++) {
            if(catalogs.get(i).getName().equals("Птицы")){
                result.remove(i);
            }
        }
        return result;
    }
}
