package com.proj.changelang.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.activities.AuthAvtivity;
import com.proj.changelang.activities.CabinetActivity;
import com.proj.changelang.activities.ShowDetails;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecycleHotAdapter extends RecyclerView.Adapter<RecycleHotAdapter.vHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private FileHelper fileHelper;
    private JSONObject object;
    private boolean can;
    public RecycleHotAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        fileHelper = new FileHelper(context);
        can =true;
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
        final Post post = posts.get(position);
        holder.nameView.setText(post.getPrice());
        holder.location.setText(post.getCityID());
        if(post.getImages().size()>0) {
                Picasso.with(context).load("http://maltabu.kz/"
                        +post.getImages().get(0).getSmall()).placeholder(R.drawable.photocounter).fit().centerCrop().into(holder.img);
                holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.img.setImageResource(R.drawable.photocounter);
            holder.photoCount.setText(String.valueOf(0));
        }

        holder.cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(can) {
                    can = false;
                    getPost(post.getNumber());
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                        can = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
        public ConstraintLayout cl1;

        public vHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.textView3);
            photoCount = (TextView) itemView.findViewById(R.id.textView11);
            location = (TextView) itemView.findViewById(R.id.textView5);
            img = (ImageView) itemView.findViewById(R.id.imageView17);
            cl1 = (ConstraintLayout) itemView.findViewById(R.id.selectedPost);
        }
    }


    public void getPost(String numb){
        final OkHttpClient client = new OkHttpClient();
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
                Gson googleJson = new Gson();
                if (s1 != null) {
                    try {
                        JSONObject postObject = new JSONObject(s1);
                        JSONArray arr = postObject.getJSONArray("images");
                    ArrayList<Image> imagesArrayList = new ArrayList<>();
                    ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                    for (int j = 0; j < imgObjList.size(); j++) {
                        JSONObject imgJson = arr.getJSONObject(j);
                        Image image = new Image(
                                imgJson.getString("extra_small"),
                                imgJson.getString("small"),
                                imgJson.getString("medium"),
                                imgJson.getString("big"));
                        imagesArrayList.add(image);
                    }
                    String phones = "";
                    JSONArray arr2 = postObject.getJSONArray("phones");
                    ArrayList ObjList = googleJson.fromJson(String.valueOf(arr2), ArrayList.class);
                    for (int k=0; k<ObjList.size();k++){
                        phones+=arr2.getString(k)+";";
                    }
                    int visitors = postObject.getJSONObject("stat").getInt("visitors");
                    String createdAt = postObject.getString("createdAt");
                    String cityID = postObject.getJSONObject("cityID").getString("name");
                    int number = postObject.getInt("number");
                    String title = postObject.getString("title");
                    String price = postObject.getJSONObject("price").getString("kind");
                    if (price.equals("value")) {
                        price = String.valueOf(postObject.getJSONObject("price").getInt("value"));
                    } else {
                        if (price.equals("trade")) {
                            if (Maltabu.lang.toLowerCase().equals("ru")) {
                                price = "Договорная цена";
                            } else {
                                String kazName = fileHelper.diction().getString("Договорная цена");
                                price = kazName;
                            }
                        } else {
                            if (price.equals("free")) {
                                if (Maltabu.lang.toLowerCase().equals("ru")) {
                                    price = "Отдам даром";
                                } else {
                                    String kazName = fileHelper.diction().getString("Отдам даром");
                                    price = kazName;
                                }
                            }
                        }
                    }
                    if (postObject.getBoolean("hasContent")) {
                        String content = postObject.getString("content");
                        Post post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList);
                        post.setPhones(phones);
                        Intent details = new Intent(context, ShowDetails.class);
                        details.putExtra("post", post);
                        context.startActivity(details);
                    } else {
                        Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList);
                        post.setPhones(phones);
                        Intent details = new Intent(context, ShowDetails.class);
                        details.putExtra("post", post);
                        context.startActivity(details);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    }


            }
        };
        asyncTask1.execute();
    }

    public String getDate(String s)    {
        String [] ss = s.split("T");
        String [] ss2 = ss[0].split("-");
        if (ss2[1].equals("01"))
        {
            ss2[1] = "Января,қаңтар";
        } else {
            if (ss2[1].equals("02"))
            {
                ss2[1] = "Февраля,ақпан";
            }
            else {
                if (ss2[1].equals("03"))
                {
                    ss2[1] = "Марта,наурыз";
                }
                else {
                    if (ss2[1].equals("04"))
                    {
                        ss2[1] = "Апреля,сәуiр";
                    } else {
                        if (ss2[1].equals("05"))
                        {
                            ss2[1] = "Мая,мамыр";
                        } else {
                            if (ss2[1].equals("06"))
                            {
                                ss2[1] = "Июня,маусым";
                            }
                            else {
                                if (ss2[1].equals("07"))
                                {
                                    ss2[1] = "Июля,шiлде";
                                } else {
                                    if (ss2[1].equals("08"))
                                    {
                                        ss2[1] = "Августа,тамыз";
                                    }
                                    else {
                                        if (ss2[1].equals("09"))
                                        {
                                            ss2[1] = "Сентября,қыркүйек";
                                        }
                                        else {
                                            if (ss2[1].equals("10"))
                                            {
                                                ss2[1] = "Октября,қазан";
                                            }
                                            else {
                                                if (ss2[1].equals("11"))
                                                {
                                                    ss2[1] = "Ноября,қараша";
                                                }
                                                else {
                                                    if (ss2[1].equals("12"))
                                                    {
                                                        ss2[1] = "Декабря,желтоқсан";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ss2[2] +","+ss2[1];
    }
}
