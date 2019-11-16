package kz.maltabu.app.maltabukz.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

public class ChooseRegistration extends AppCompatActivity{
    private ConstraintLayout phoneReg, mailReg;
    private Intent phoneRegIntent, mailRegIntent;
    private TextView phoneText, mailText;
    private ImageView arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_registration);
        mailRegIntent = new Intent(this,RegisterAvtivity.class);
        phoneRegIntent = new Intent(this,PhoneRegisterAvtivity.class);
        phoneReg = findViewById(R.id.constraintLayout40);
        mailReg = findViewById(R.id.constraintLayout39);
        phoneText = findViewById(R.id.phoneText);
        mailText = findViewById(R.id.mailText);
        arr = findViewById(R.id.arr);
        initLIsteners();

    }

    private void initLIsteners() {
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        Resources res = context.getResources();
        phoneText.setText(res.getString(R.string.chooseReg1));
        mailText.setText(res.getString(R.string.chooseReg2));
        phoneReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(phoneRegIntent);
                finish();
            }
        });
        mailReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mailRegIntent);
                finish();
            }
        });
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAnimator().animateViewBound(arr);
                startActivity(new Intent(ChooseRegistration.this, AuthAvtivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, AuthAvtivity.class));
        finish();
    }

}
