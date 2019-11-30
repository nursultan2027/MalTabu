package kz.maltabu.app.maltabukz.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ActivityFragment;
import kz.maltabu.app.maltabukz.activities.AddPostActivity;
import kz.maltabu.app.maltabukz.activities.AddPostActivity2;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Catalog;
import kz.maltabu.app.maltabukz.models.Category;
import kz.maltabu.app.maltabukz.models.Comment;

public class CategoryAddPostAdapter extends RecyclerView.Adapter<CategoryAddPostAdapter.ViewHolder>{

    private ArrayList<Catalog> categories;
    private Context context;
    private JSONObject dictionary;
    private FileHelper fileHelper;
    private ActivityFragment activityFragment;
    public CategoryAddPostAdapter(ArrayList<Catalog> categories, Context context, ActivityFragment activityFragment) {
        this.categories = categories;
        this.context = context;
        this.activityFragment =activityFragment;
        fileHelper = new FileHelper(context);
        try {
            dictionary = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Catalog catalog = categories.get(position);
        if(Maltabu.lang.equals("ru"))
            holder.nameView.setText(catalog.getName());
        else {
            try {
                holder.nameView.setText(dictionary.getString(catalog.getName()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondThread secondThread = new SecondThread(catalog);
                secondThread.run();
                activityFragment.openCurrentFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameView ;
        private ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.select);
        }
    }

    private void Go(Catalog catalog){
        Intent intent2 = new Intent(context, AddPostActivity2.class);
        intent2.putExtra("catalog", catalog);
        context.startActivity(intent2);
    }


    public class SecondThread extends Thread{
        Catalog catalog;
        SecondThread(Catalog catalog){
            this.catalog = catalog;
        }
        @Override
        public void run() {
            Go(catalog);
        }
    }

}
