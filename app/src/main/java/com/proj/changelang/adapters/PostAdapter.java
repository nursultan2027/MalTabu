package com.proj.changelang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.models.Item;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;

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
        Post post = posts.get(position);
        TextView nameView = (TextView) view.findViewById(R.id.textView3);
        TextView nameView2 = (TextView) view.findViewById(R.id.textView4);
        TextView nameView3 = (TextView) view.findViewById(R.id.textView5);
        TextView photoCount = (TextView) view.findViewById(R.id.textView11);
        TextView visitors = (TextView) view.findViewById(R.id.textView10);
        ImageView img = (ImageView) view.findViewById(R.id.imageView17);
        nameView.setText(post.getTitle());
        visitors.setText(post.getVisitors());
        nameView2.setText(post.getPrice());
        nameView3.setText(post.getCityID()+", "+post.getCreatedAt());
        if(post.getImages().size()>0) {
            Picasso.with(getContext()).load("http://maltabu.kz/"+post.getImages().get(0).getSmall()).into(img);
            photoCount.setText(String.valueOf(post.getImages().size()));
        }
        return view;
    }
}
