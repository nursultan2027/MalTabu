package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {
    private Button button;
    private ConnectionHelper connectionHelper;
    private ImageView arr;
    private FileHelper fileHelper;
    private boolean ok = true;
    private EditText oldPass, newPass, confPass;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        button = (Button) findViewById(R.id.button4);
        arr = (ImageView) findViewById(R.id.arr);
        fileHelper = new FileHelper(this);
        connectionHelper = new ConnectionHelper(this);
        oldPass = (EditText) findViewById(R.id.editText13);
        newPass = (EditText) findViewById(R.id.editText14);
        confPass = (EditText) findViewById(R.id.editText15);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mInterstitialAd = new InterstitialAd(ChangePassword.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8576417478026387/6126966096");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                startActivity(new Intent(ChangePassword.this, AuthAvtivity.class));
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newPass.getText().toString().equals(confPass.getText().toString()))
                    if (connectionHelper.isConnected())
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
                .url("https://maltabu.kz/v1/api/clients/self/pass")
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
                            if (mInterstitialAd.isLoaded())
                                mInterstitialAd.show();
                            else {
                                startActivity(new Intent(ChangePassword.this, AuthAvtivity.class));
                                finish();
                            }
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
            if (mInterstitialAd.isLoaded())
                mInterstitialAd.show();
            else {
                startActivity(new Intent(ChangePassword.this, AuthAvtivity.class));
                finish();
            }
        }
    }
}
