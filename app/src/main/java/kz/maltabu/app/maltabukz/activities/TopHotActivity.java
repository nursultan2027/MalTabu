package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.redmadrobot.inputmask.MaskedTextChangedListener;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TopHotActivity extends AppCompatActivity{

    private ImageView arr;
    private TextView numb, score;
    private String phone, code, promoType;
    private Dialog dialog, progressDialog;
    private FileHelper fileHelper;
    private ConnectionHelper connectionHelper;
    private JSONObject userObject;
    private Button addScore, topButton, hotButton, topButtonMobile, hotButtonMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_top_activity);
        fileHelper = new FileHelper(this);
        connectionHelper = new ConnectionHelper(this);
        numb = (TextView) findViewById(R.id.title);
        addScore = (Button)findViewById(R.id.addScore);
        score = (TextView) findViewById(R.id.textView58);
        arr = (ImageView) findViewById(R.id.arr);
        topButton = (Button) findViewById(R.id.topButton);
        hotButton = (Button) findViewById(R.id.hotButton);
        topButtonMobile = findViewById(R.id.topButtonMobile);
        hotButtonMobile = findViewById(R.id.hotButtonMobile);
        dialog = new Dialog(this);
        progressDialog = new Dialog(this);
        String cab = null;
        try {
            userObject = new JSONObject(fileHelper.readUserFile());
            score.setText(String.valueOf(userObject.getInt("balance"))+" ед.");
            cab = userObject.getString("number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        numb.setText(getIntent().getStringExtra("rrr"));
        final String finalCab = cab;
        addScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent payment = new Intent(TopHotActivity.this, AddScore.class);
                payment.putExtra("numb", finalCab);
                startActivity(payment);
            }
        });
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(userObject.getInt("balance")>149) {
                        if (connectionHelper.isConnected())
                            rise(getIntent().getStringExtra("number"));
                        else
                            Toast.makeText(TopHotActivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(userObject.getInt("balance")>249) {
                        if (connectionHelper.isConnected())
                            hot(getIntent().getStringExtra("number"));
                        else
                            Toast.makeText(TopHotActivity.this, "Нет подключения", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        topButtonMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoType="150";
                phoneDialog();
            }
        });
        hotButtonMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoType="250";
                phoneDialog();
            }
        });
    }

    private void hot(String numer) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("number", numer)
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/promotion/hot")
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
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
                    setContentView(R.layout.success);
                    TextView textView = (TextView) findViewById(R.id.textView46);
                    textView.setText(getString(R.string.successPay));
                    arr = (ImageView) findViewById(R.id.arr);
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        };
        asyncTask.execute();
    }

    private void rise(String numer) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("number", numer)
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/promotion/rise")
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
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
                    setContentView(R.layout.success);
                    TextView textView = (TextView) findViewById(R.id.textView46);
                    textView.setText(getString(R.string.successPay));
                    arr = (ImageView) findViewById(R.id.arr);
                    arr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        };
        asyncTask.execute();
    }

    private void phoneDialog(){
        dialog.setContentView(R.layout.dialog_send_smss);
        EditText phoneText = dialog.findViewById(R.id.phone_number);
        final TextView errorText = dialog.findViewById(R.id.error_text);
        Button sendSmsButton = dialog.findViewById(R.id.buttonOkok);
        MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                phoneText,
                "+7 ([000]) [000]-[00]-[00]",
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull final String formattedValue) {
                        if(extractedValue.length()==10){
                            phone=extractedValue;
                        } else {
                            phone="";
                        }
                    }
                }
        );
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phone.isEmpty()) {
                    sendSms();
                    dialog.dismiss();
                    showProgressDialog();
                }
                else
                    errorText.setText("Phone Number");
            }
        });
        phoneText.setHint(listener.placeholder());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void codeDialog(){
        dialog.setContentView(R.layout.dialog_send_code);
        TextView intro = dialog.findViewById(R.id.changeText);
        intro.setText("После введения кода подтверждения с Вашего баланса будет списано"+ promoType+"₸");
        final EditText phoneText = dialog.findViewById(R.id.confirm_code);
        Button sendCodeButton = dialog.findViewById(R.id.buttonOkok);
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(phoneText.getText().toString().length()==6) {
                        code = phoneText.getText().toString();
                        sendConfirmCode();
                        dialog.dismiss();
                        showProgressDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void sendSms(){
            final OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("phone", phone)
                    .build();
            final Request request = new Request.Builder()
                    .url("https://maltabu.kz/v1/api/clients/promotion/sendConfirmSMS")
                    .addHeader("isAuthorized", Maltabu.isAuth)
                    .addHeader("token", Maltabu.token)
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
                            JSONObject result = new JSONObject(s);
                            progressDialog.dismiss();
                            if(result.has("message")){
                                if(result.getString("message").toLowerCase().equals("Success".toLowerCase())){
                                    codeDialog();
                                }
                            } else {
                                if(result.has("name")){
                                    if(result.getString("name").equals("notAceptable")){
                                        Toast.makeText(TopHotActivity.this, "Оператор сотовой связи не обслуживается", Toast.LENGTH_SHORT).show();
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

    private void sendConfirmCode() throws JSONException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("amount", promoType)
                .add("clientID", userObject.getString("_id"))
                .add("phone", phone)
                .add("code", code)
                .build();
        final Request request = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/promotion/sendConfirmCode")
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
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
                        JSONObject result = new JSONObject(s);
                        progressDialog.dismiss();
                        if(result.has("message")){
                            if(result.getString("message").toLowerCase().equals("Success".toLowerCase())){
                                if(promoType.equals("150"))
                                    rise(getIntent().getStringExtra("number"));
                                else
                                    hot(getIntent().getStringExtra("number"));
                            }
                        } else {
                            if(result.has("name")){
                                if(result.getString("name").equals("notAceptable")){
                                    Toast.makeText(TopHotActivity.this, "Недостаточно средств на счету или Неверный код подтверждения", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(TopHotActivity.this, "Ошибка на стороне сервера", Toast.LENGTH_SHORT).show();
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

    private void showProgressDialog(){
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
    }
}
