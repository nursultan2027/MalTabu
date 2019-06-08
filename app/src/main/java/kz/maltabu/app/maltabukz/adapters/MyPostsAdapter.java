package kz.maltabu.app.maltabukz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.PostAtMyPosts;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        String date = getDate(post.getCreatedAt());
        String [] asd = date.split(",");
        if(Maltabu.lang.equals("ru"))
            date = asd[0]+" "+asd[1];
        else
            date = asd[0]+" "+asd[2];
        title1.setText(post.getName());
        title2.setText(post.getTitle());
        title3.setText(date);
        if(post.getImg().size()>0) {
            Picasso.with(getContext()).load("https://maltabu.kz/" + post.getImg().get(0)).into(img);
        }
        return view;
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