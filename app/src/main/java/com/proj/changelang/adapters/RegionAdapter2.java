package com.proj.changelang.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.activities.FilterActivity;
import com.proj.changelang.activities.SecondSelect2;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Region;

import org.json.JSONException;

import java.util.ArrayList;

public class RegionAdapter2 extends ArrayAdapter<Region> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Region> categories;

    public RegionAdapter2(Context context, int resource, ArrayList<Region> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView nameView = (TextView) view.findViewById(R.id.select);
        final Region region = categories.get(position);
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.slectedRegion);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextSelect = new Intent(getContext(), FilterActivity.class);
                Maltabu.Region = region;
                Maltabu.RegionFilter = region.getName();
                Maltabu.regId = region.getId();
                Maltabu.CityFilter = null;
                getContext().startActivity(nextSelect);
                ((Activity)getContext()).finish();
            }
        });
        String kazName=null;
        try {
            kazName = Maltabu.jsonObject.getString(region.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Maltabu.lang.toLowerCase().equals("ru")) {
            nameView.setText(region.getName());
        } else {
            nameView.setText(kazName);
        }
        return view;
    }
}
