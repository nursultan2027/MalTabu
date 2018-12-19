package com.proj.changelang.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.proj.changelang.R;
import com.proj.changelang.helpers.InputValidation;
import com.proj.changelang.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterAvtivity extends AppCompatActivity {
    private TextView rules, email406;
    private CheckBox agree;
    private InputValidation validation;
    private ImageView arr;
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
        arr = (ImageView)findViewById(R.id.arr);
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
                if(Check()) {
                    email406.setText("");
                    registration();
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
                        JSONObject obj = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        asyncTask.execute();

    }
    public void MakeToast(){
        Toast.makeText(this, "Данный электронный адрес уже существует", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, AuthAvtivity.class));
        finish();
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
}
