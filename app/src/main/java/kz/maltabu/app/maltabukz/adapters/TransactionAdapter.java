package kz.maltabu.app.maltabukz.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import kz.maltabu.app.maltabukz.R;

import kz.maltabu.app.maltabukz.activities.ShowDetails;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import kz.maltabu.app.maltabukz.models.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private LayoutInflater inflater;
    private int layout;
    private FileHelper fileHelper;
    private ArrayList<Transaction> transactions;

    public TransactionAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super(context, resource, transactions);
        this.transactions = transactions;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);
        final Transaction transaction = transactions.get(position);
        TextView date = (TextView) view.findViewById(R.id.date);
        fileHelper = new FileHelper(getContext());
//        TextView time = (TextView) view.findViewById(R.id.time);
        TextView value = (TextView) view.findViewById(R.id.value);
        TextView title = (TextView) view.findViewById(R.id.postTitle);
        TextView kind = (TextView) view.findViewById(R.id.postKind);
        ConstraintLayout select = (ConstraintLayout) view.findViewById(R.id.selected);
        String [] ss = transaction.getCreatedAt().split("uu");
//        time.setText(ss[1]);
        String dates [] = ss[0].split(",");
        if (Maltabu.lang.equals("ru"))
            date.setText(dates[0]+" "+dates[1]);
        else
            date.setText(dates[0]+" "+dates[2]);
        if(transaction.getSource().equals("cabinet")) {
            title.setText(transaction.getTitle());
            value.setText("-" + transaction.getValue());
            if(transaction.getValue().equals("250")){
                kind.setText("Добавление в горячие");
            } else if(transaction.getValue().equals("150")){
                kind.setText("Поднятие наверх");
            }
        }
        else {
            value.setText("+" + transaction.getValue());
            kind.setText("Пополнение через Касса 24");
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondThread thread = new SecondThread(transaction.getPostNumber());
                thread.start();
            }
        });

        return view;
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
                            Intent details = new Intent(getContext(), ShowDetails.class);
                            details.putExtra("post", post);
                            getContext().startActivity(details);
                        } else {
                            Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList);
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