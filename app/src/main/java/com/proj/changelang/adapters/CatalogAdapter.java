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
import com.proj.changelang.activities.MainActivity;
import com.proj.changelang.activities.MainActivity2;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogAdapter extends ArrayAdapter<Catalog> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Catalog> cities;
    private FileHelper fileHelper;

    public CatalogAdapter(Context context, int resource, ArrayList<Catalog> cities) {
        super(context, resource, cities);
        this.cities = cities;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView nameView = (TextView) view.findViewById(R.id.select);
        final Catalog city = cities.get(position);
        String kazName=null;
        fileHelper = new FileHelper(getContext());
        ConstraintLayout constraintLayout = view.findViewById(R.id.slectedRegion);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextSelect = new Intent(getContext(), MainActivity2.class);
                Maltabu.s2 = city.getName();
                getContext().startActivity(nextSelect);
                ((Activity)getContext()).finish();
            }
        });
        try {
            kazName = new JSONObject(fileHelper.readDictionary()).getString(city.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Maltabu.lang.toLowerCase().equals("ru")) {
            nameView.setText(city.getName());
        } else {
            nameView.setText(kazName);
        }
        nameView.setText(city.getName());
        return view;
    }
}
