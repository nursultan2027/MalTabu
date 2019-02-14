package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private FileHelper fileHelper;
    private Dialog epicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asd);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
        if(!fileHelper.readToken().isEmpty()){
            Maltabu.token=fileHelper.readToken();
            Maltabu.isAuth="true";
        }
        if (isConnected())
        {
            GetVersion();
        } else {
            setContentView(R.layout.no_internet_connection);
            TextView refresh = (TextView) findViewById(R.id.textView35);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnected()){
                        GetCategories();
                        GetDictionary();
                    }
                }
            });
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


    public void GetVersion()
    {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/data/apps")
                .get()
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        GetCategories();
                        GetDictionary();
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
                    try {
                        JSONObject jsonObject = new JSONObject(s1);
                        if (jsonObject.has("android_version")) {
                            if (Maltabu.version.equals(jsonObject.getString("android_version"))) {
                                GetCategories();
                                GetDictionary();
                            }
                            else{
                                if(jsonObject.has("android_link"))
                                {
                                    String url = jsonObject.getString("android_link");
                                    sDialog(url);
                                }
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


    protected void sDialog(final String uri) {
        epicDialog.setContentView(R.layout.auth_logout);
        final Button asd = (Button) epicDialog.findViewById(R.id.buttonCancel);
        final Button asd3 = (Button) epicDialog.findViewById(R.id.buttonOkok);
        asd.setTextColor(Color.parseColor("#69aef3"));
        final TextView asd2 = (TextView) epicDialog.findViewById(R.id.changeText);
        asd2.setText("Обновить приложение");
        asd.setText("Обновить");
        asd3.setText("Потом");
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(browserIntent);
            }
        });
        asd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (epicDialog != null && epicDialog.isShowing()) {
                    epicDialog.dismiss();
                }
                GetCategories();
                GetDictionary();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}