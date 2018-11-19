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

import com.proj.changelang.R;
import com.proj.changelang.activities.ShowDetails;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotPostAdapter extends ArrayAdapter<Post> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Post> posts;

    public HotPostAdapter(Context context, int resource, ArrayList<Post> post) {
        super(context, resource, post);
        this.posts = post;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final Post post = posts.get(position);
        ConstraintLayout cl1 = (ConstraintLayout) view.findViewById(R.id.selectedPost);
        TextView nameView2 = (TextView) view.findViewById(R.id.textView3);
        TextView nameView3 = (TextView) view.findViewById(R.id.textView5);
        TextView photoCount = (TextView) view.findViewById(R.id.textView11);
        ImageView img = (ImageView) view.findViewById(R.id.imageView17);
        nameView2.setText(post.getPrice());
        if (Maltabu.lang.equals("ru")) {
            nameView3.setText(post.getCityID());
        }
        else {
            try {
                nameView3.setText(Maltabu.jsonObject.getString(post.getCityID()));
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
                getPost(post.getNumber());
                Intent details = new Intent(getContext(), ShowDetails.class);
                details.putExtra("post", post);
                getContext().startActivity(details);
            }
        });
        return view;
    }

    public void getPost(String numb)
    {
        final OkHttpClient client = new OkHttpClient();
        Post post = null;
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/posts/"+numb)
                .get()
                .addHeader("isAuthorized", "false")
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {

                }
            }
        };
        asyncTask1.execute();
    }

}
