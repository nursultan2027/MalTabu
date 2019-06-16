package kz.maltabu.app.maltabukz.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;

public class NoConnection extends AppCompatActivity{

    private TextView refresh;
    private ConnectionHelper connectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection);
        refresh = (TextView) findViewById(R.id.textView35);
        connectionHelper = new ConnectionHelper(this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()){
                    finish();
                }
            }
        });
    }
}
