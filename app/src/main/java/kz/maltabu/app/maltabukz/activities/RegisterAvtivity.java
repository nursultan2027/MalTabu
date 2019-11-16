package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;
import kz.maltabu.app.maltabukz.helpers.InputValidation;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterAvtivity extends AppCompatActivity {
    private TextView rules, email406, title1,title2,title3,title4, posk1,posk2,posk3,posk4;
    private CheckBox agree;
    private InputValidation validation;
    private ImageView arr;
    private String email;
    private Resources res;
    private Button register;
    private Dialog epicDialog;
    private ConnectionHelper connectionHelper;
    private EditText name, mail, pass1, pass2;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        epicDialog = new Dialog(this);
        connectionHelper = new ConnectionHelper(this);
        validation = new InputValidation(this);
        email406 = (TextView) findViewById(R.id.sameEmail);
        agree = (CheckBox) findViewById(R.id.checkBox5);
        register = (Button) findViewById(R.id.register);
        rules = (TextView) findViewById(R.id.textView25);
        name = (EditText) findViewById(R.id.editText9);
        mail = (EditText) findViewById(R.id.editText10);
        pass1 = (EditText) findViewById(R.id.editText11);
        pass2 = (EditText) findViewById(R.id.editText12);
        title1 = (TextView) findViewById(R.id.reg21);
        title2 = (TextView) findViewById(R.id.textView24);
        title3 = (TextView) findViewById(R.id.reg31);
        title4 = (TextView) findViewById(R.id.reg41);
        posk1 = (TextView) findViewById(R.id.reg12);
        posk2 = (TextView) findViewById(R.id.reg22);
        posk3 = (TextView) findViewById(R.id.reg32);
        posk4 = (TextView) findViewById(R.id.reg42);
        arr = (ImageView)findViewById(R.id.arr);
        mInterstitialAd = new InterstitialAd(RegisterAvtivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8576417478026387/6126966096");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                startActivity(new Intent(RegisterAvtivity.this, AuthAvtivity.class));
                finish();
            }
        });
        UpdateViews();
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAvtivity.this, ChooseRegistration.class));
                finish();
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAvtivity.this, PdfActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    if (Check()) {
                        sDialog();
                        registration();
                    }
                } else {
                    Toast.makeText(RegisterAvtivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                }
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void registration() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name", name.getText().toString())
                .add("mail", mail.getText().toString())
                .add("password", pass1.getText().toString())
                .add("confPass", pass2.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/reg")
                .addHeader("isAuthorized", "false")
                .post(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        return response.body().string();
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
                    epicDialog.dismiss();
                    try {
                        email = mail.getText().toString();
                        JSONObject obj = new JSONObject(s);
                        if(obj.has("message")) {
                            if (obj.getString("message").toLowerCase().equals("created")) {
                                setContentView(R.layout.activate_cabinet);
                                arr = (ImageView) findViewById(R.id.arr);
                                arr.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (mInterstitialAd.isLoaded())
                                            mInterstitialAd.show();
                                        else {
                                            startActivity(new Intent(RegisterAvtivity.this, AuthAvtivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                        else {
                            if (obj.has("name")){
                                if(obj.getString("name").toLowerCase().equals("notaceptable")){
                                    Toast.makeText(RegisterAvtivity.this,res.getString(R.string.mailExist),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ChooseRegistration.class));
        finish();
    }

    public void sendActivationMail(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/reg?mail="+email)
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
                }
            }
        };
        asyncTask1.execute();
    }

    public boolean Check(){
        if(!validation.isInputEditTextFilled(name)){
            Toast.makeText(this, "Name", Toast.LENGTH_LONG).show();
            return false;
        } else {
        if(!validation.isInputEditTextEmail(mail)){
            Toast.makeText(this, "invalid EMAIL", Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                if(pass1.getText().toString().length()<6){
                    Toast.makeText(this, "more 6", Toast.LENGTH_LONG).show();
                    return false;
                }
                else {
                    if(!pass1.getText().toString().equals(pass2.getText().toString())){
                        Toast.makeText(this, "wrong password confirm", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    else {
                        if(!agree.isChecked()){
                            Toast.makeText(this, "checkbox", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void UpdateViews(){
        res = LocaleHelper.setLocale(this, Maltabu.lang).getResources();
        title1.setText(res.getString(R.string.reg11));
        title2.setText(res.getString(R.string.reg21));
        title3.setText(res.getString(R.string.reg31));
        title4.setText(res.getString(R.string.reg41));
        posk1.setText(res.getString(R.string.reg12));
        posk2.setText(res.getString(R.string.reg22));
        posk3.setText(res.getString(R.string.reg32));
        posk4.setText(res.getString(R.string.reg42));
        rules.setText(res.getString(R.string.rules2));
        register.setText(res.getString(R.string.regButton));
    }
}
