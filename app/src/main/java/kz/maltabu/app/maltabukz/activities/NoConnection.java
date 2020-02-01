package kz.maltabu.app.maltabukz.activities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;

public class NoConnection extends AppCompatActivity{

    private TextView refresh;
    private ConnectionHelper connectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection);
        refresh = (TextView) findViewById(R.id.textView35);
        connectionHelper = new ConnectionHelper(this);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()){
                    finish();
                } else {
                    CustomAnimator.animateViewBound(refresh);
                }
            }
        });
    }
}
