package kz.maltabu.app.maltabukz.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.activities.MainActivity2;
import kz.maltabu.app.maltabukz.activities.SecondSelect2;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Region;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegionAdapter extends ArrayAdapter<Region> {
    private LayoutInflater inflater;
    private int layout;
    private FileHelper fileHelper;
    private ArrayList<Region> categories;

    public RegionAdapter(Context context, int resource, ArrayList<Region> categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        TextView nameView = (TextView) view.findViewById(R.id.select);
        final Region region = categories.get(position);
        fileHelper = new FileHelper(getContext());
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.slectedRegion);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0){
                    Maltabu.s4=categories.get(position).getName();
                    Maltabu.s3=null;
                    Maltabu.s6=null;
                    Maltabu.searchPage=1;
                    Intent nextSelect = new Intent(getContext(), MainActivity2.class);
                    getContext().startActivity(nextSelect);
                    ((Activity) getContext()).finish();
                }
                else {
                    Intent nextSelect = new Intent(getContext(), SecondSelect2.class);
                    nextSelect.putExtra(Region.class.getCanonicalName(), region);
                    getContext().startActivity(nextSelect);
                    ((Activity) getContext()).finish();
                }
            }
        });
        String kazName=null;
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
        return view;
    }
}
