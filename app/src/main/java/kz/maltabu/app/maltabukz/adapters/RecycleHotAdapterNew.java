package kz.maltabu.app.maltabukz.adapters;

import android.content.Context;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Post;

public class RecycleHotAdapterNew extends RecyclerView.Adapter<RecycleHotAdapterNew.vHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private FileHelper fileHelper;
    private JSONObject object;
    private boolean can;
    public RecycleHotAdapterNew(ArrayList<Post> posts, Context context) {
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
        if(Maltabu.lang.equals("ru")) {
            holder.location.setText(post.getCityID());
        } else {
            try {
                if(object!=null && post.getCityID()!=null)
                    holder.location.setText(object.getString(post.getCityID()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(post.getImages().size()>0) {
            if(post.getImages().get(0).getSmall().contains("http"))
                Picasso.with(context).load(post.getImages().get(0).getSmall()).placeholder(R.drawable.listempty).fit().centerCrop().into(holder.img);
            else
                Picasso.with(context).load("https://maltabu.kz/"+post.getImages().get(0).getSmall()).placeholder(R.drawable.listempty).fit().centerCrop().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.img.setImageResource(R.drawable.listempty);
            holder.photoCount.setText(String.valueOf(0));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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


    public void getPost(String numb){
        Intent details = new Intent(context, ShowDetails.class);
        details.putExtra("postNumb", numb);
        context.startActivity(details);
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
