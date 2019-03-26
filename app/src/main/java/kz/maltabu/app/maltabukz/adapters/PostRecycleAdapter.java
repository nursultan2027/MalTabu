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
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostRecycleAdapter extends RecyclerView.Adapter<PostRecycleAdapter.ViewHolder>{

    private ArrayList<Post> posts;
    private Context context;
    private FileHelper fileHelper;
    private JSONObject object;
    private Dialog epicDialog;
    public PostRecycleAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
        this.fileHelper = new FileHelper(context);
        epicDialog = new Dialog(context);
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
        if(post.getImages().size()>0) {
            Picasso.with(context).load("http://maltabu.kz/"
                    +post.getImages().get(0).getSmall()).placeholder(R.drawable.listempty).centerCrop().fit().into(holder.img);
            holder.photoCount.setText(String.valueOf(post.getImages().size()));
        } else {
            holder.img.setImageDrawable(context.getDrawable(R.drawable.listempty));
            holder.photoCount.setText(String.valueOf(0));
        }
        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog();
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
                        Image image = null;
                        Comment com = null;
                        for (int j = 0; j < imgObjList.size(); j++) {
                            JSONObject imgJson = arr.getJSONObject(j);
                            image = new Image(
                                    imgJson.getString("extra_small"),
                                    imgJson.getString("small"),
                                    imgJson.getString("medium"),
                                    imgJson.getString("big"));
                            imagesArrayList.add(image);
                        }

                        JSONArray commentsArr = postObject.getJSONArray("comments");
                        ArrayList<Comment> commentsArrayList = new ArrayList<>();
                        ArrayList commObjList = googleJson.fromJson(String.valueOf(commentsArr), ArrayList.class);
                        for (int k = 0; k < commObjList.size(); k++) {
                            JSONObject imgJson = commentsArr.getJSONObject(k);
                            com = new Comment(
                                    imgJson.getString("content"),
                                    imgJson.getString("createdAt"),
                                    imgJson.getString("name"),
                                    imgJson.getString("mail"));
                            commentsArrayList.add(com);
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
                            price = uiPrice(String.valueOf(postObject.getJSONObject("price").getInt("value")))+" ₸";
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
                            Post post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList, commentsArrayList);
                            post.setPhones(phones);
                            Intent details = new Intent(context, ShowDetails.class);
                            details.putExtra("post", post);
                            context.startActivity(details);
                            if (epicDialog != null && epicDialog.isShowing()) {
                                epicDialog.dismiss();
                            }
                        } else {
                            Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList, commentsArrayList);
                            post.setPhones(phones);
                            Intent details = new Intent(context, ShowDetails.class);
                            details.putExtra("post", post);
                            context.startActivity(details);
                            if (epicDialog != null && epicDialog.isShowing()) {
                                epicDialog.dismiss();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        };
        asyncTask1.execute();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
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

    public String uiPrice(String value){
        int length = value.length();
        String result = value;
        if(length>3){
            if(length==4){
                result = value.substring(0,1)+" "+value.substring(1);
            } else {
                if(length==5){
                    result = value.substring(0,2)+" "+value.substring(2);
                } else {
                    if(length==6){
                        result = value.substring(0,3)+" "+value.substring(3);
                    } else {
                        if(length==7){
                            result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4);
                        } else {
                            if(length==8){
                                result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5);
                            } else {
                                if(length==9){
                                    result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6);
                                } else {
                                    if(length==10){
                                        result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7);
                                    } else {
                                        if(length==11){
                                            result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8);
                                        } else {
                                            if(length==12){
                                                result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9);
                                            } else {
                                                if(length==13){
                                                    result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7,10)+" "+value.substring(10);
                                                } else {
                                                    if(length==14){
                                                        result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8,11)+" "+value.substring(11);
                                                    } else {
                                                        if(length==15){
                                                            result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9,12)+" "+value.substring(12);
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
        }

        return result;
    }
}
