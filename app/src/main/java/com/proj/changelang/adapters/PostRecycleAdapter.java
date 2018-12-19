package com.proj.changelang.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.activities.ShowDetails;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;

public class PostRecycleAdapter extends RecyclerView.Adapter<PostRecycleAdapter.ViewHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private Post post;
    private FileHelper fileHelper;
    private JSONObject object;
    public PostRecycleAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        this.fileHelper = new FileHelper(context);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        post = posts.get(position);
        holder.nameView.setText(post.getTitle());
        holder.visitors.setText(post.getVisitors());
        holder.nameView2.setText(post.getPrice());
        String dates [] = post.getCreatedAt().split(",");
        if (Maltabu.lang.equals("ru")) {
            holder.nameView3.setText(post.getCityID()+", "+dates[0]+ " "+dates[1]);
        } else {
            try {
                holder.nameView3.setText(object.getString(post.getCityID())
                        +", "+dates[0]+ " "+dates[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(post.getImages().size()>0) {
            Picasso.with(context).load("http://maltabu.kz/"
                    +post.getImages().get(0).getSmall()).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.img.setImageDrawable(context.getDrawable(R.drawable.ic_action_photo));
            holder.photoCount.setText(String.valueOf(0));
        }
        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(context, ShowDetails.class);
                details.putExtra("post", post);
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameView ;
        public TextView nameView2;
        public TextView nameView3;
        public TextView photoCount;
        public TextView visitors;
        public ConstraintLayout selected;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            selected = (ConstraintLayout) itemView.findViewById(R.id.selectedPost);
            nameView = (TextView) itemView.findViewById(R.id.textView3);
            nameView2 = (TextView) itemView.findViewById(R.id.textView4);
            nameView3 = (TextView) itemView.findViewById(R.id.textView5);
            photoCount = (TextView) itemView.findViewById(R.id.textView11);
            visitors = (TextView) itemView.findViewById(R.id.textView10);
            img = (ImageView) itemView.findViewById(R.id.imageView17);
        }
    }
}
