package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthAvtivity extends AppCompatActivity {
    private Button register, auth;
    private ImageView arr;
    private Dialog epicDialog;
    private FileHelper fileHelper;
    private EditText edtLog, edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize_activity);
        register = (Button) findViewById(R.id.button3);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
        arr = (ImageView)findViewById(R.id.arr);
        auth = (Button) findViewById(R.id.button2);
        edtLog = (EditText) findViewById(R.id.editText9);
        edtPass = (EditText) findViewById(R.id.editText10);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthAvtivity.this, MainActivity2.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthAvtivity.this, RegisterAvtivity.class));
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    if (CheckEditTexts()) {
                        sDialog();
                        Author();
                    }
                } else {
                    Toast.makeText(AuthAvtivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Author() {
            final OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("login", edtLog.getText().toString())
                    .add("password", edtPass.getText().toString())
                    .build();
            final Request request = new Request.Builder()
                    .url("http://maltabu.kz/v1/api/login/clients")
                    .addHeader("isAuthorized",Maltabu.isAuth)
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
                        try {
                            JSONObject obj = new JSONObject(s);
                            String pageName = obj.getString("token");
                            fileHelper.writeToken(pageName);
                            Maltabu.token = pageName;
                            Maltabu.isAuth = "true";
                            getPosting();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncTask.execute();
        }

    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

    private boolean CheckEditTexts(){
        if(edtLog.getText().toString().isEmpty()||edtPass.getText().toString().isEmpty())
            return false;
        return true;
    }

    public void getUser(){
            final OkHttpClient client = new OkHttpClient();
            final Request request2 = new Request.Builder()
                    .url("http://maltabu.kz/v1/api/login/clients")
                    .get()
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
                        fileHelper.writeUserFile(s1);
                        epicDialog.dismiss();
                        startActivity(new Intent(AuthAvtivity.this, CabinetActivity.class));
                        finish();
                    }
                }
            };
            asyncTask1.execute();
    }

    public void getPosting(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/cabinet/posting")
                .get()
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
                    fileHelper.writePostingFile(s1);
                    getUser();
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
