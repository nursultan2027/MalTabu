package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.proj.changelang.R;

public class RegisterAvtivity extends AppCompatActivity {
    private TextView rules;
    private ImageView arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        rules = (TextView) findViewById(R.id.textView25);
        arr = (ImageView)findViewById(R.id.arr);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAvtivity.this, MainActivity2.class));
                finish();
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAvtivity.this, PdfActivity.class));
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public void onBackPressed() {;
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}
