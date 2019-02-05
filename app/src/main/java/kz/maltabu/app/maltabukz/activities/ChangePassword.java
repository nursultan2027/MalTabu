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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {
    private Button button;
    private ImageView arr;
    private FileHelper fileHelper;
    private boolean ok = true;
    private EditText oldPass, newPass, confPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        button = (Button) findViewById(R.id.button4);
        arr = (ImageView) findViewById(R.id.arr);
        fileHelper = new FileHelper(this);
        oldPass = (EditText) findViewById(R.id.editText13);
        newPass = (EditText) findViewById(R.id.editText14);
        confPass = (EditText) findViewById(R.id.editText15);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newPass.getText().toString().equals(confPass.getText().toString()))
                    if (isConnected())
                        changePass();
                    else
                        Toast.makeText(ChangePassword.this, "Нет подключения", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ChangePassword.this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changePass() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("oldPass", oldPass.getText().toString())
                .add("pass", newPass.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/self/pass")
                .addHeader("isAuthorized", Maltabu.isAuth.toLowerCase())
                .addHeader("token", Maltabu.token)
                .put(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Toast.makeText(ChangePassword.this, "Неправильный старый пароль", Toast.LENGTH_LONG).show();
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
                    setContentView(R.layout.no_posts);
                    ok = false;
                    arr = (ImageView) findViewById(R.id.arr);
                    Maltabu.isAuth ="false";
                    Maltabu.token = null;
                    fileHelper.writeUserFile("");
                    fileHelper.writeToken("");
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ChangePassword.this, AuthAvtivity.class));
                            finish();
                            }
                    });
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        if(ok) {
            finish();
        } else {
            startActivity(new Intent(ChangePassword.this, AuthAvtivity.class));
            finish();
        }
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
