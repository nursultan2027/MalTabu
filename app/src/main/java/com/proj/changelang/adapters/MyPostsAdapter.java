package com.proj.changelang.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.activities.ShowDetails;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;
import com.proj.changelang.models.PostAtMyPosts;
import com.proj.changelang.models.Transaction;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyPostsAdapter extends ArrayAdapter<PostAtMyPosts> {
    private LayoutInflater inflater;
    private int layout;
    private FileHelper fileHelper;
    private ArrayList<PostAtMyPosts> myPosts;

    public MyPostsAdapter(Context context, int resource, ArrayList<PostAtMyPosts> posts) {
        super(context, resource, posts);
        this.myPosts = posts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final PostAtMyPosts post = myPosts.get(position);
        TextView title1 = (TextView) view.findViewById(R.id.textView63);
        TextView title2 = (TextView) view.findViewById(R.id.textView62);
        TextView title3 = (TextView) view.findViewById(R.id.textView64);
        ImageView img = (ImageView) view.findViewById(R.id.imgg);
        title1.setText(post.getName());
        title1.setText(post.getTitle());
        title1.setText(post.getCreatedAt());
        if(post.getImg().size()>0) {
            Picasso.with(getContext()).load("http://maltabu.kz/" + post.getImg().get(0)).into(img);
        }
        return view;
    }
}