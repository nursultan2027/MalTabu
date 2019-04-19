package kz.maltabu.app.maltabukz;

import android.app.Application;
import android.content.Context;

import kz.maltabu.app.maltabukz.helpers.LocaleHelper;

public class MainApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"kk"));
    }
}
