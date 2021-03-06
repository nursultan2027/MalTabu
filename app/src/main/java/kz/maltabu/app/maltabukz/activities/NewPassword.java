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
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewPassword extends AppCompatActivity {
    private Button button;
    private ImageView arr;
    private EditText newPass, confPass;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);
        button = (Button) findViewById(R.id.button4);
        arr = (ImageView) findViewById(R.id.arr);
        newPass = (EditText) findViewById(R.id.editText14);
        confPass = (EditText) findViewById(R.id.editText15);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mInterstitialAd = new InterstitialAd(NewPassword.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8576417478026387/6126966096");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                startActivity(new Intent(NewPassword.this, AuthAvtivity.class));
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPass.getText().toString().equals(confPass.getText().toString())) {
                    changePass();
                }
                else
                    Toast.makeText(NewPassword.this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void changePass() {
        final OkHttpClient client = new OkHttpClient();
        String mail = getIntent().getStringExtra("mail");
        RequestBody formBody = new FormBody.Builder()
                .add("mail", mail)
                .add("pass", newPass.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/newPassword")
                .addHeader("isAuthorized", Maltabu.isAuth.toLowerCase())
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
                    setContentView(R.layout.activate_cabinet_phone);
                    arr = (ImageView) findViewById(R.id.arr);
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mInterstitialAd.isLoaded())
                                mInterstitialAd.show();
                            else {
                                startActivity(new Intent(NewPassword.this, AuthAvtivity.class));
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
            startActivity(new Intent(NewPassword.this, AuthAvtivity.class));
            finish();
    }
}
