package kz.maltabu.app.maltabukz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import kz.maltabu.app.maltabukz.R;

public class UpdatePassword extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize_activity);
        button = (Button) findViewById(R.id.button2);
        final Intent intent = getIntent();
        final String action = intent.getAction();
        final String data = intent.getDataString();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            button.setText(data);
        }
    }
}
