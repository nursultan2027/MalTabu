package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.helpers.InputValidation;
import com.proj.changelang.helpers.LocaleHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    private Button register;
    private Dialog epicDialog;
    private EditText name, mail, pass1, pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        epicDialog = new Dialog(this);
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
        UpdateViews();
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAvtivity.this, AuthAvtivity.class));
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
                if(isConnected()) {
                    if (Check()) {
                        email406.setText("");
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
                .url("http://maltabu.kz/v1/api/clients/self/reg")
                .addHeader("isAuthorized", "false")
                .post(formBody)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        String result = response.body().string().toString();
                        JSONObject asd = new JSONObject(result);
                        if(asd.getString("name").toLowerCase().equals("notaceptable")){
                            email406.setText("Данный электронный адрес уже существует");
                        }
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
                        email = mail.getText().toString();
                        JSONObject obj = new JSONObject(s);
                        if(obj.getString("message").toLowerCase().equals("created")){
                            setContentView(R.layout.activate_cabinet);
                            arr = (ImageView)findViewById(R.id.arr);
                            register = (Button) findViewById(R.id.again);
                            arr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(RegisterAvtivity.this, AuthAvtivity.class));
                                    finish();
                                }
                            });
                            register.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sendActivationMail();
                                }
                            });
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
    public void onBackPressed() {;
        startActivity(new Intent(this, AuthAvtivity.class));
        finish();
    }

    public void sendActivationMail(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/self/reg?mail="+email)
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

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void UpdateViews(){
        Resources res = LocaleHelper.setLocale(this, Maltabu.lang).getResources();
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
