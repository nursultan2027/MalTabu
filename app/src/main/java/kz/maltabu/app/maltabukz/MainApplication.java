package kz.maltabu.app.maltabukz;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import io.fabric.sdk.android.Fabric;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;

public class MainApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"kz"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(getApplicationContext());
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
}
