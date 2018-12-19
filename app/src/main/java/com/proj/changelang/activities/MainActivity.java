package com.proj.changelang.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.proj.changelang.R;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asd);
        fileHelper = new FileHelper(this);
        if(!fileHelper.readToken().isEmpty()){
            Maltabu.token=fileHelper.readToken();
            Maltabu.isAuth="true";
        }
        if (isConnected())
        {
            GetCategories();
            GetDictionary();
        }
    }
    public void GetCategories() {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/data")
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
                if (s1 != null) {
                    fileHelper.writeDataFile(s1);
                }
            }
        };
        asyncTask1.execute();
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
    public void GetDictionary() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://maltabu.kz/dist/translations/kk_KZ.json")
                .get()
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
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
                        JSONObject dict = new JSONObject(s).getJSONObject("kk_KZ");
                        fileHelper.writeDictionary(dict.toString());
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask1.execute();
    }
}
