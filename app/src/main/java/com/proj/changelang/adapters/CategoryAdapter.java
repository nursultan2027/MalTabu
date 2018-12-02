package com.proj.changelang.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.activities.FirstSelect2;
import com.proj.changelang.activities.SecondSelect2;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.Region;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import io.paperdb.Paper;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private LayoutInflater inflater;
    private int layout;
    private FileHelper fileHelper;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, int resource, ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView nameView = (TextView) view.findViewById(R.id.select);
        fileHelper = new FileHelper(getContext());
        final Category region = categories.get(position);
        String kazName=null;
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.slectedRegion);
        try {
            kazName = new JSONObject(fileHelper.readDictionary()).getString(region.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Maltabu.lang.toLowerCase().equals("ru")) {
            nameView.setText(region.getName());
        } else {
            nameView.setText(kazName);
        }
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextSelect = new Intent(getContext(), FirstSelect2.class);
                nextSelect.putExtra(Category.class.getCanonicalName(), region);
                getContext().startActivity(nextSelect);
                ((Activity)getContext()).finish();
            }
        });
        return view;
    }
}
