package com.proj.changelang;

import android.app.Application;
import android.content.Context;

import com.proj.changelang.helpers.LocaleHelper;

import java.util.Locale;

public class MainApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"ru"));
    }
}
