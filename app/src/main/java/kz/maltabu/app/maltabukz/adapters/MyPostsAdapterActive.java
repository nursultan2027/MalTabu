package kz.maltabu.app.maltabukz.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.EditPostActivity;
import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.activities.TopHotActivity;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.InputValidation;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
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
    private Dialog newDialog;
    private FileHelper fileHelper;
    private InputValidation inputValidation;
    private ArrayList<PostAtMyPosts> myPosts;

    public MyPostsAdapterActive(Context context, int resource, ArrayList<PostAtMyPosts> posts) {
        super(context, resource, posts);
        this.myPosts = posts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.epicDialog = new Dialog(getContext());
        this.newDialog = new Dialog(getContext());
        this.inputValidation = new InputValidation(getContext());
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
        String date = this.inputValidation.getDate(post.getCreatedAt());
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
            if(post.getImg().get(0).contains("http"))
                Picasso.with(getContext()).load(post.getImg().get(0)).placeholder(R.drawable.listempty).fit().centerCrop().into(img);
            else
                Picasso.with(getContext()).load("https://maltabu.kz/"+post.getImg().get(0)).placeholder(R.drawable.listempty).fit().centerCrop().into(img);
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
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewDialog();
                Intent editActivityIntent = new Intent(getContext(), EditPostActivity.class);
                editActivityIntent.putExtra("number", post.getNumber());
                editActivityIntent.putExtra("adID", post.getAdID());
                editActivityIntent.putExtra("catalogID", post.getCatalogID());
                newDialog.dismiss();
                getContext().startActivity(editActivityIntent);
            }
        });
        return view;
    }

    public void getPost(String numb){
        Intent details = new Intent(getContext(), ShowDetails.class);
        details.putExtra("postNumb", numb);
        getContext().startActivity(details);
    }

    @Override
    public int getCount() {
        return myPosts.size();
    }

    public void archPost(final int position, String number){
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/cabinet/posts/"+number+"/archive")
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

    protected void showNewDialog(){
        newDialog.setContentView(R.layout.progress_dialog);
        newDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newDialog.show();
    }
}