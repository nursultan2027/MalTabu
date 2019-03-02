package kz.maltabu.app.maltabukz.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import kz.maltabu.app.maltabukz.activities.TopHotActivity;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import kz.maltabu.app.maltabukz.models.PostAtMyPosts;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyPostsAdapterActive extends ArrayAdapter<PostAtMyPosts> {
    private LayoutInflater inflater;
    private int layout;
    private Dialog epicDialog;
    private FileHelper fileHelper;
    private ArrayList<PostAtMyPosts> myPosts;

    public MyPostsAdapterActive(Context context, int resource, ArrayList<PostAtMyPosts> posts) {
        super(context, resource, posts);
        this.myPosts = posts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.epicDialog = new Dialog(getContext());
        this.fileHelper = new FileHelper(getContext());
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final PostAtMyPosts post = myPosts.get(position);
        TextView title = (TextView) view.findViewById(R.id.textView65);
        TextView price = (TextView) view.findViewById(R.id.textView96);
        TextView date1 = (TextView) view.findViewById(R.id.textView68);
        TextView catalog = (TextView) view.findViewById(R.id.textView67);
        TextView visitorsCount = (TextView) view.findViewById(R.id.visitorsCount);
        TextView phonesCount = (TextView) view.findViewById(R.id.phonesVisCount);
        TextView commentsCount = (TextView) view.findViewById(R.id.commentsCount);
        ImageView img = (ImageView) view.findViewById(R.id.imageView38);
        Button btn1 = (Button) view.findViewById(R.id.button7);
        Button btn2 = (Button) view.findViewById(R.id.bunntonHot);
        Button btn3 = (Button) view.findViewById(R.id.button72);
        Button btn4 = (Button) view.findViewById(R.id.bunntonHot2);
        String date = getDate(post.getCreatedAt());
        String [] asd = date.split(",");
        if(Maltabu.lang.equals("ru"))
            date = asd[0]+" "+asd[1];
        else
            date = asd[0]+" "+asd[2];
        title.setText(post.getTitle());
        String priceText="";
        if(post.getPrice()=="Отдам даром"||post.getPrice()=="Договорная цена")
        {
            if (Maltabu.lang.toLowerCase().equals("ru")) {
                priceText = post.getPrice();
            } else {
                String kazName = null;
                try {
                    kazName = fileHelper.diction().getString(post.getPrice());
                    priceText = kazName;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            priceText = post.getPrice();
        }
        price.setText(priceText);
        date1.setText(date);
        catalog.setText(post.getCatalog());
        phonesCount.setText(post.getVisitors());
        visitorsCount.setText(post.getPhones());
        commentsCount.setText(post.getComments());
        if(post.getImg().size()>0) {
            Picasso.with(getContext()).load("http://maltabu.kz/" + post.getImg().get(0)).placeholder(R.drawable.listempty).fit().centerCrop().into(img);
        } else {
            img.setImageResource(R.drawable.listempty);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondThread thread = new SecondThread(post.getNumber());
                thread.start();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog(position, post.getNumber());
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotIntent = new Intent(getContext(), TopHotActivity.class);
                hotIntent.putExtra("number", post.getNumber());
                hotIntent.putExtra("rrr", post.getTitle());
                getContext().startActivity(hotIntent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotIntent = new Intent(getContext(), TopHotActivity.class);
                hotIntent.putExtra("number", post.getNumber());
                hotIntent.putExtra("rrr", post.getTitle());
                getContext().startActivity(hotIntent);
            }
        });
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
                            price = String.valueOf(postObject.getJSONObject("price").getInt("value")+" ₸");
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
                            Post post = new Post(visitors, getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                            post.setPhones(phones);
                            Intent details = new Intent(getContext(), ShowDetails.class);
                            details.putExtra("post", post);
                            getContext().startActivity(details);
                        } else {
                            Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                            post.setPhones(phones);
                            Intent details = new Intent(getContext(), ShowDetails.class);
                            details.putExtra("post", post);
                            getContext().startActivity(details);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        };
        asyncTask1.execute();
    }

    public void archPost(final int position, String number){
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/cabinet/posts/"+number+"/archive")
                .post(formBody)
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
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
                    myPosts.remove(position);
                    notifyDataSetChanged();
                    epicDialog.dismiss();
                }
            }
        };
        asyncTask1.execute();
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

    protected void sDialog(final int position, final String number) {
        epicDialog.setContentView(R.layout.auth_logout);
        final Button asd = (Button) epicDialog.findViewById(R.id.buttonCancel);
        final Button asd3 = (Button) epicDialog.findViewById(R.id.buttonOkok);
        asd.setTextColor(Color.parseColor("#69aef3"));
        final TextView asd2 = (TextView) epicDialog.findViewById(R.id.changeText);
        asd2.setText("Удалить объявление");
        asd.setText("Удалить");
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archPost(position, number);
                epicDialog.setContentView(R.layout.progress_bar);
            }
        });
        asd3.setText("Отмена");
        asd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}