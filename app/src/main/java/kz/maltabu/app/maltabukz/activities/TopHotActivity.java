package kz.maltabu.app.maltabukz.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TopHotActivity extends AppCompatActivity{

    private ImageView arr;
    private TextView numb, score;
    private FileHelper fileHelper;
    private JSONObject userObject;
    private Button addScore, topButton, hotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_top_activity);
        fileHelper = new FileHelper(this);
        numb = (TextView) findViewById(R.id.title);
        addScore = (Button)findViewById(R.id.addScore);
        score = (TextView) findViewById(R.id.textView58);
        arr = (ImageView) findViewById(R.id.arr);
        topButton = (Button) findViewById(R.id.topButton);
        hotButton = (Button) findViewById(R.id.hotButton);
        String cab = null;
        try {
            userObject = new JSONObject(fileHelper.readUserFile());
            score.setText(String.valueOf(userObject.getInt("balance"))+" ед.");
            cab = userObject.getString("number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        numb.setText(getIntent().getStringExtra("rrr"));
        final String finalCab = cab;
        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent payment = new Intent(TopHotActivity.this, AddScore.class);
                payment.putExtra("numb", finalCab);
                startActivity(payment);
            }
        });
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(userObject.getInt("balance")>149) {
                        if (isConnected())
                            rise(getIntent().getStringExtra("number"));
                        else
                            Toast.makeText(TopHotActivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(userObject.getInt("balance")>249) {
                        if (isConnected())
                            hot(getIntent().getStringExtra("number"));
                        else
                            Toast.makeText(TopHotActivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void hot(String numer) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("number", numer)
                .build();
        final Request request = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/promotion/hot")
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .post(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    setContentView(R.layout.success);
                    arr = (ImageView) findViewById(R.id.arr);
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        };
        asyncTask.execute();
    }

    private void rise(String numer) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("number", numer)
                .build();
        final Request request = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/promotion/rise")
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .post(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    setContentView(R.layout.success);
                    arr = (ImageView) findViewById(R.id.arr);
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        };
        asyncTask.execute();
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
        }
        return connected;
    }
}