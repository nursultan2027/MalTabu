package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import org.json.JSONException;
import org.json.JSONObject;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPasswordPhone extends AppCompatActivity {
    private Button phone;
    private EditText input;
    private ImageView arr;
    private String email,phoneStr;
    private Dialog epicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetChooseView();
    }

    public void SetChooseView(){
        setContentView(R.layout.forget_pass_phone_activity);
        epicDialog = new Dialog(this);
        input = (EditText) findViewById(R.id.editText17);
        arr = (ImageView) findViewById(R.id.arr);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordPhone.this, ForgetPassword.class));
                finish();
            }
        });
        phone = (Button) findViewById(R.id.button2);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phoneStr.isEmpty()) {
                    sDialog();
                    recoverPass();
                }
            }
        });
        MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                input,
                "+7 ([000]) [000]-[00]-[00]",
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull final String formattedValue) {
                        if(extractedValue.length()==10){
                            phoneStr=extractedValue;
                        } else {
                            phoneStr="";
                        }
                    }
                }
        );
        input.setHint(listener.placeholder());
    }

    private void recoverPass() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("mail", phoneStr)
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/forget")
                .addHeader("isAuthorized", Maltabu.isAuth)
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
                super.onPostExecute(s);{
                    try {
                        epicDialog.dismiss();
                        JSONObject obj = new JSONObject(s);
                        if(obj.has("message")){
                            if(obj.getString("message").toLowerCase().equals("success")){
                                setContentView(R.layout.forget_pass_code_activity);
                                input = (EditText) findViewById(R.id.editText17);
                                phone = (Button) findViewById(R.id.button2);
                                phone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(input.getText().toString().length()==4){
                                            codeConfirm();
                                        }
                                    }
                                });
                            }
                        } else {
                            if(obj.has("name")){
                                if (obj.getString("name").toLowerCase().equals("norecord")) {
                                    Toast.makeText(ForgetPasswordPhone.this, "Нет такого пользователя", Toast.LENGTH_LONG).show();
                                } else {
                                    if(obj.getString("name").toLowerCase().equals("notaceptable")){
                                        Toast.makeText(ForgetPasswordPhone.this, "Сервер перегружен! Попробуте чуть позже", Toast.LENGTH_LONG).show();
                                    }
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

    private void codeConfirm() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("code", input.getText().toString())
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/code")
                .addHeader("isAuthorized", Maltabu.isAuth)
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
                super.onPostExecute(s);{
                    try {
                        epicDialog.dismiss();
                        JSONObject obj = new JSONObject(s);
                        if(obj.has("message")){
                            if(obj.getString("message").toLowerCase().equals("success")){
                                Intent newPass = new Intent(ForgetPasswordPhone.this, NewPassword.class);
                                newPass.putExtra("mail", phoneStr);
                                startActivity(newPass);
                            }
                        } else {
                            if(obj.has("name")){
                                if(obj.getString("name").toLowerCase().equals("norecord")){
                                    Toast.makeText(ForgetPasswordPhone.this, getResources().getString(R.string.wrongCode), Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {;
        startActivity(new Intent(this, ForgetPassword.class));
        finish();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
