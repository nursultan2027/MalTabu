package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.CommentRecycleAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.FilterModel;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentRecycleAdapter adapter;
    private TextView title;
    private FileHelper fileHelper;
    private Button sendComment;
    private EditText editText;
    private Dialog epicDialog;
    private ImageView arr;
    private Post post;
    private ArrayList<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
        post = getIntent().getParcelableExtra("post");
        initViews();
        initList();
    }

    public void initViews(){
        arr = (ImageView) findViewById(R.id.arr);
        recyclerView = (RecyclerView) findViewById(R.id.comments);
        title=(TextView)findViewById(R.id.textView66);
        sendComment=(Button) findViewById(R.id.button8);
        editText = (EditText) findViewById(R.id.editText16);

    }

    public void initList(){
        comments = post.getComments();
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        Resources resources = context.getResources();
        title.setText(resources.getString(R.string.comments1));
        sendComment.setText(resources.getString(R.string.comments2));
        if(Maltabu.isAuth.equals("false")){
            sendComment.setText(resources.getString(R.string.auth2));
            editText.setText(resources.getString(R.string.comments3));
            editText.setEnabled(false);
            sendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent AuthIntent = new Intent(CommentsActivity.this, AuthAvtivity.class);
                    startActivity(AuthIntent);
                    finish();
                }
            });
        } else {
            sendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editText.getText().toString().isEmpty())
                    {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(fileHelper.readUserFile());
                            String name = object.getString("name");

                            Comment comment = new Comment();
                            comment.setContent(editText.getText().toString());
                            comment.setCreatedAt("");
                            comment.setName(name);
                            comments.add(comment);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sDialog();
                        post();
                    }
                }
            });
        }
        adapter = new CommentRecycleAdapter(comments,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog();
                SecondThread thread = new SecondThread(post.getNumber());
                thread.start();
            }
        });
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
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
                            Intent details = new Intent(CommentsActivity.this, ShowDetails.class);
                            details.putExtra("post", post);
                            startActivity(details);
                            finish();
                        } else {
                            Post post = new Post(visitors, getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                            post.setPhones(phones);
                            Intent details = new Intent(CommentsActivity.this, ShowDetails.class);
                            details.putExtra("post", post);
                            startActivity(details);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        };
        asyncTask1.execute();
    }

    @Override
    public void onBackPressed() {
        sDialog();
        SecondThread thread = new SecondThread(post.getNumber());
        thread.start();
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


    private void post() {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {


            @Override
            protected String doInBackground(String... urls) {
                try {
                    try {
                        return HttpPost2("http://maltabu.kz/v1/api/clients/posts/"+post.getNumber()+"/comments");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return "Error!";
                    }
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid.";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (epicDialog != null && epicDialog.isShowing()) {
                    editText.setText("");
                    epicDialog.dismiss();
                }
            }
        };
        task.execute();
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    private JSONObject buidJsonObject2() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("content", editText.getText().toString());
        jsonObject.accumulate("postNum", Integer.parseInt(post.getNumber()));
        jsonObject.accumulate("isReply", false);
        return jsonObject;
    }

    private String HttpPost2(String myUrl) throws IOException, JSONException {
        StringBuilder res = new StringBuilder();
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("isAuthorized", "true");
        conn.setRequestProperty("token", Maltabu.token);
        conn.setDoOutput(true);
        JSONObject jsonObject = buidJsonObject2();
        setPostRequestContent(conn, jsonObject);
        conn.connect();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            res.append(line);
        }
        return res.toString();
    }
}
