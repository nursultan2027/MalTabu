package kz.maltabu.app.maltabukz.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostRecycleAdapterNew extends RecyclerView.Adapter<PostRecycleAdapterNew.ViewHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private FileHelper fileHelper;
    private JSONObject object;
    private String catalogID;
    public PostRecycleAdapterNew(ArrayList<Post> posts, Context context, String catalogId) {
        this.posts = posts;
        this.context = context;
        this.catalogID = catalogId;
        this.fileHelper = new FileHelper(context);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public PostRecycleAdapterNew(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        this.catalogID = null;
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
        final Post post = posts.get(position);
        holder.nameView.setText(post.getTitle());
        holder.visitors.setText(post.getVisitors());
        holder.nameView2.setText(post.getPrice());
        holder.commCount.setText(String.valueOf(post.getComments().size()));
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
        int res = 0;
        if(catalogID!=null) {
            res = getImgSrc(catalogID);
        } else {
            res = R.drawable.listempty;
        }
        if(post.getImages().size()>0) {
            Picasso.with(context).load("https://maltabu.kz/"
                    +post.getImages().get(0).getExtra_small()).placeholder(res).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.img.setImageDrawable(context.getDrawable(res));
            holder.photoCount.setText(String.valueOf(0));
        }
        if(post.isPromoted()){
            holder.top.setVisibility(View.VISIBLE);
        } else {
            holder.top.setVisibility(View.GONE);
        }
        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondThread thread = new SecondThread(post.getNumber());
                thread.start();
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
        public TextView commCount;
        public TextView visitors;
        public ConstraintLayout selected;
        public ImageView img;
        public ImageView top;

        public ViewHolder(View itemView) {
            super(itemView);
            selected = (ConstraintLayout) itemView.findViewById(R.id.selectedPost);
            nameView = (TextView) itemView.findViewById(R.id.textView3);
            nameView2 = (TextView) itemView.findViewById(R.id.textView4);
            nameView3 = (TextView) itemView.findViewById(R.id.textView5);
            commCount = (TextView) itemView.findViewById(R.id.textView9);
            photoCount = (TextView) itemView.findViewById(R.id.textView11);
            visitors = (TextView) itemView.findViewById(R.id.textView10);
            img = (ImageView) itemView.findViewById(R.id.imageView17);
            top = (ImageView) itemView.findViewById(R.id.topIcon);
        }
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

    private int getImgSrc(String id){
        switch (id) {
            case("5ab672c9559d5e049c25a62c"):
                return R.drawable.cammel;
            case("5ab672c9559d5e049c25a62d"):
                return R.drawable.horse;
            case("5ab672c9559d5e049c25a62e"):
                return R.drawable.cow;
            case("5ab672c9559d5e049c25a62f"):
                return R.drawable.sheep;
            case("5ab672c9559d5e049c25a630"):
                return R.drawable.chicken;
            case("5ab672c9559d5e049c25a631"):
                return R.drawable.goat;
            case("5ab672c9559d5e049c25a632"):
                return R.drawable.rabbit;
            case("5ab672c9559d5e049c25a645"):
                return R.drawable.dog;
            case("5ab672c9559d5e049c25a646"):
                return R.drawable.cat;
            case("5ab672c9559d5e049c25a647"):
                return R.drawable.parrot;
            case("5ab672c9559d5e049c25a648"):
                return R.drawable.hamster;
            case("5ab672c9559d5e049c25a649"):
                return R.drawable.goldfish;
            case("5ab672c9559d5e049c25a64a"):
                return R.drawable.turtle;
            case("5b0bffe2530c6256285a19b1"):
                return R.drawable.equipment;
            case("5b0bffe2530c6256285a1933"):
                return R.drawable.tractor;
            case("5ab672c9559d5e049c25a643"):
                return R.drawable.otherservice;
            case("5ab672c9559d5e049c25a642"):
                return R.drawable.medother;
            case("5ab672c9559d5e049c25a641"):
                return R.drawable.training;
            case("5ab672c9559d5e049c25a640"):
                return R.drawable.shaver;
            case("5ab672c9559d5e049c25a63f"):
                return R.drawable.transportation;
            case("5ab672c9559d5e049c25a638"):
                return R.drawable.otherfeed;
            case("5ab672c9559d5e049c25a637"):
                return R.drawable.dobavki;
            case("5ab672c9559d5e049c25a636"):
                return R.drawable.grain;
            case("5ab672c9559d5e049c25a635"):
                return R.drawable.hay;
            case("5ab672c9559d5e049c25a634"):
                return R.drawable.kombikorm;
            case("5ab672c9559d5e049c25a64c"):
                return R.drawable.meat;
            case("5ab672c9559d5e049c25a64d"):
                return R.drawable.milk;
            case("5ab672c9559d5e049c25a64e"):
                return R.drawable.vegetables;
            case("5ab672c9559d5e049c25a64f"):
                return R.drawable.fruits;
            case("5ab672c9559d5e049c25a650"):
                return R.drawable.otherproducts;
            case("5afeb741d151e32d5cc245c5"):
                return R.drawable.job;
            case("5afeb741d151e32d5cc245c4"):
                return R.drawable.work2;
            case("5ab672c9559d5e049c25a63b"):
                return R.drawable.wholesale2;
            case("5ab672c9559d5e049c25a63a"):
                return R.drawable.wholesale;

        }
        return R.drawable.listempty;
    }
    private void getPost(String numb){
        Intent details = new Intent(context, ShowDetails.class);
        details.putExtra("postNumb", numb);
        context.startActivity(details);
    }


    public class SecondThread extends Thread{
        String numb;
        SecondThread (String numb){
            this.numb = numb;
        }

        @Override
        public void run() {
            getPost(numb);
        }
    }
}
