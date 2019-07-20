package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.ArrayList;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.CommentRecycleAdapter;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.Post;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommentRecycleAdapter adapter;
    private TextView title;
    private String addedComment="";
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
            editText.setGravity(Gravity.CENTER);
            editText.setTextColor(resources.getColor(R.color.White));
            editText.setBackgroundColor(resources.getColor(R.color.MaltabuYellow));
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
                            addedComment = editText.getText().toString();
                            comment.setContent(addedComment);
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
                SecondThread thread = new SecondThread();
                thread.start();
            }
        });
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    public void getPost(){
        Intent details = new Intent();
        details.setData(Uri.parse(addedComment));
        setResult(RESULT_OK, details);
        finish();
    }

    @Override
    public void onBackPressed() {
        sDialog();
        SecondThread thread = new SecondThread();
        thread.start();
    }

    public class SecondThread extends Thread{
        SecondThread (){ }

        @Override
        public void run() {
            getPost();
        }
    }


    private void post() {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {


            @Override
            protected String doInBackground(String... urls) {
                try {
                    try {
                        return HttpPost2("https://maltabu.kz/v1/api/clients/posts/"+post.getNumber()+"/comments");
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
