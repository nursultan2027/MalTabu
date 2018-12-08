package com.proj.changelang.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    private LayoutInflater inflater;
    private int layout;
    private JSONObject object;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, int resource, ArrayList<Post> post) throws JSONException {
        super(context, resource, post);
        this.posts = post;
        this.layout = resource;
        this.object = new JSONObject(new FileHelper(getContext()).readDictionary());
        this.inflater = LayoutInflater.from(context);
    }

    public static final class ViewHolder {
        ConstraintLayout cl1;
        TextView nameView;
        TextView nameView2;
        TextView nameView3;
        TextView photoCount;
        TextView visitors;
        ImageView img;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Post post = posts.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            holder = new ViewHolder();
            holder.cl1 = (ConstraintLayout) convertView.findViewById(R.id.selectedPost);
            holder.nameView = (TextView) convertView.findViewById(R.id.textView3);
            holder.nameView2 = (TextView) convertView.findViewById(R.id.textView4);
            holder.nameView3 = (TextView) convertView.findViewById(R.id.textView5);
            holder.photoCount = (TextView) convertView.findViewById(R.id.textView11);
            holder.visitors = (TextView) convertView.findViewById(R.id.textView10);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView17);
            convertView.setTag(holder);
        }
        else {
           holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(post.getTitle());
        holder.visitors.setText(post.getVisitors());
        holder.nameView2.setText(post.getPrice());
        String dates [] = post.getCreatedAt().split(",");
        if (Maltabu.lang.equals("ru")) {
            holder.nameView3.setText(post.getCityID()+", "+dates[0]+ " "+dates[1]);
        }
        else {
            try {
                holder.nameView3.setText(object.getString(post.getCityID())
                        +", "+dates[0]+ " "+dates[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(post.getImages().size()>0) {
            Picasso.with(getContext()).load("http://maltabu.kz/"
                    +post.getImages().get(0).getSmall()).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImages().size()));
        }

        holder.cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(getContext(), ShowDetails.class);
                details.putExtra("post", post);
                getContext().startActivity(details);
            }
        });
        return convertView;
    }

}
