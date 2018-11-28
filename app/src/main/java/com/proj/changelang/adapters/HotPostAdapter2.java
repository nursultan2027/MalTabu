package com.proj.changelang.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HotPostAdapter2 extends BaseAdapter {
    private Context mContext;
    private ArrayList<Post> posts;

    public HotPostAdapter2(Context context, ArrayList<Post> post) {
        this.mContext = context;
        this.posts = post;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        final Post post = posts.get(position);
        if (convertView == null) {
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.item_hot, parent, false);
        } else {
            grid = (View) convertView;
        }
        ConstraintLayout cl1 = (ConstraintLayout) grid.findViewById(R.id.selectedPost);
        TextView nameView2 = (TextView) grid.findViewById(R.id.textView3);
        TextView nameView3 = (TextView) grid.findViewById(R.id.textView5);
        TextView photoCount = (TextView) grid.findViewById(R.id.textView11);
        ImageView img = (ImageView) grid.findViewById(R.id.imageView17);
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
            Picasso.with(mContext).load("http://maltabu.kz/"
                    +post.getImages().get(0).getSmall()).centerCrop().fit().into(img);
            photoCount.setText(String.valueOf(post.getImages().size()));
        }
        return grid;
    }

//    public void getPost(String numb)
//    {
//        final OkHttpClient client = new OkHttpClient();
//        Post post = null;
//        final Request request2 = new Request.Builder()
//                .url("http://maltabu.kz/v1/api/clients/posts/"+numb)
//                .get()
//                .addHeader("isAuthorized", "false")
//                .build();
//        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                try {
//                    Response response2 = client.newCall(request2).execute();
//                    if (!response2.isSuccessful()) {
//                        return null;
//                    }
//                    return response2.body().string();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            protected void onPostExecute(String s1) {
//                super.onPostExecute(s1);
//                if (s1 != null) {
//
//                }
//            }
//        };
//        asyncTask1.execute();
//    }

}
