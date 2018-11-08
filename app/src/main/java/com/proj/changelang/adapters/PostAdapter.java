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
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, int resource, ArrayList<Post> post) {
        super(context, resource, post);
        this.posts = post;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final Post post = posts.get(position);
        ConstraintLayout cl1 = (ConstraintLayout) view.findViewById(R.id.selectedPost);
        TextView nameView = (TextView) view.findViewById(R.id.textView3);
        TextView nameView2 = (TextView) view.findViewById(R.id.textView4);
        TextView nameView3 = (TextView) view.findViewById(R.id.textView5);
        TextView photoCount = (TextView) view.findViewById(R.id.textView11);
        TextView visitors = (TextView) view.findViewById(R.id.textView10);
        ImageView img = (ImageView) view.findViewById(R.id.imageView17);
        nameView.setText(post.getTitle());
        visitors.setText(post.getVisitors());
        nameView2.setText(post.getPrice());
        String dates [] = post.getCreatedAt().split(",");
        if (Maltabu.lang.equals("ru")) {
            nameView3.setText(post.getCityID()+", "+dates[0]+ " "+dates[1]);
        }
        else {
            try {
                nameView3.setText(Maltabu.jsonObject.getString(post.getCityID())
                        +", "+dates[0]+ " "+dates[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(post.getImages().size()>0) {
            Picasso.with(getContext()).load("http://maltabu.kz/"
                    +post.getImages().get(0).getSmall()).centerCrop().fit().into(img);
            photoCount.setText(String.valueOf(post.getImages().size()));
        }

        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(getContext(), ShowDetails.class);
                details.putExtra("post", post);
                getContext().startActivity(details);
            }
        });
        return view;
    }

}
