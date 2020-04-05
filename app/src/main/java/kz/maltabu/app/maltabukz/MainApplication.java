package kz.maltabu.app.maltabukz;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import java.io.File;

import io.fabric.sdk.android.Fabric;
import io.paperdb.Paper;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;

public class MainApplication extends Application{
    public static final String CHANNEL_ID = "channelFirebase";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"kz"));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        Fabric.with(getApplicationContext());
        try {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        } catch (Exception e){
            Log.d("ERROR", e.getMessage());
        };
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(Maltabu.API_key).build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
        createNotificationChannel();
        setDefaultSettings();
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Firebase Notification Channell",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void setDefaultSettings(){
        Maltabu.token = new FileHelper(this).readToken();
        Maltabu.lang = Paper.book().read("language");
    }
}
