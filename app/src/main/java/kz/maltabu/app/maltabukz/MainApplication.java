package kz.maltabu.app.maltabukz;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;

public class MainApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"kk"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(getApplicationContext());
    }
}
