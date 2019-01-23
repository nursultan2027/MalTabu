package kz.maltabu.app.maltabukz.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maltabu.app.R;
import kz.maltabu.app.maltabukz.activities.FirstSelect2;
import kz.maltabu.app.maltabukz.activities.MainActivity2;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Category;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public View getView(final int position, View convertView, ViewGroup parent) {
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
                if(position==0){
                    Maltabu.s2=categories.get(position).getName();
                    Maltabu.s1=null;
                    Maltabu.s5=null;
                    Maltabu.searchPage=1;
                    Intent nextSelect = new Intent(getContext(), MainActivity2.class);
                    getContext().startActivity(nextSelect);
                    ((Activity) getContext()).finish();
                }
                else {
                    Intent nextSelect = new Intent(getContext(), FirstSelect2.class);
                    nextSelect.putExtra(Category.class.getCanonicalName(), region);
                    getContext().startActivity(nextSelect);
                    ((Activity) getContext()).finish();
                }
            }
        });
        return view;
    }
}
