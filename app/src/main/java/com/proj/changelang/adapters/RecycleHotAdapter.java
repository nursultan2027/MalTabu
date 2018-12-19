package com.proj.changelang.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RecycleHotAdapter extends RecyclerView.Adapter<RecycleHotAdapter.vHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private Post post;
    private FileHelper fileHelper;
    private JSONObject object;
    private int h;
    private int w;
    public RecycleHotAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        fileHelper = new FileHelper(context);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public vHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_hot,parent,false);
        return new vHolder(v);
    }

    @Override
    public void onBindViewHolder(final vHolder holder, int position) {
        post = posts.get(position);
        holder.nameView.setText(post.getPrice());
        holder.location.setText(post.getCityID());
        if(post.getImages().size()>0) {
                Picasso.with(context).load("http://maltabu.kz/"
                        +post.getImages().get(0).getSmall()).placeholder(R.drawable.photocounter).fit().centerCrop().into(holder.img);
                holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.photoCount.setText(String.valueOf(0));
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class vHolder extends RecyclerView.ViewHolder{
        public TextView nameView;
        public TextView photoCount;
        public TextView location;

        public ImageView img;

        public vHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.textView3);
            photoCount = (TextView) itemView.findViewById(R.id.textView11);
            location = (TextView) itemView.findViewById(R.id.textView5);
            img = (ImageView) itemView.findViewById(R.id.imageView17);
        }
    }
}
