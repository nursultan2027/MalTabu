package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import kz.maltabu.app.maltabukz.R;

public class ForgetPassword extends AppCompatActivity {
    private Button phone,mail;
    private ImageView arr;
    private Intent auth, loginPhone, loginMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetChooseView();
    }

    public void SetChooseView(){
        setContentView(R.layout.forget_pass_activity);
        loginPhone = new Intent(this,ForgetPasswordPhone.class);
        auth = new Intent(ForgetPassword.this, AuthAvtivity.class);
        arr = (ImageView) findViewById(R.id.arr);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(auth);
                finish();
            }
        });
        phone = (Button) findViewById(R.id.button2);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginPhone);
                finish();
            }
        });
        mail = (Button) findViewById(R.id.button10);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, AuthAvtivity.class));
        finish();
    }
}
