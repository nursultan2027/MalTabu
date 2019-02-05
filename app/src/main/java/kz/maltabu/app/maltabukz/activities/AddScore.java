package kz.maltabu.app.maltabukz.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;

public class AddScore extends AppCompatActivity{

    private ImageView arr;
    private TextView numb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        numb = (TextView) findViewById(R.id.idd);
        arr = (ImageView) findViewById(R.id.arr);
        numb.setText(getIntent().getStringExtra("numb"));
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
