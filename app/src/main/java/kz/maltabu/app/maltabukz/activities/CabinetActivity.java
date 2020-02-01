package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.adapters.ViewPagerAdapter;
import kz.maltabu.app.maltabukz.fragments.MyPostsFragment;
import kz.maltabu.app.maltabukz.fragments.MyProfileFragment;
import kz.maltabu.app.maltabukz.fragments.MyScoreFragment;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class CabinetActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Dialog epicDialog;
    private ImageView arr;
    private FileHelper fileHelper;
    private JSONObject object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabinet);
        fileHelper = new FileHelper(this);
        if(fileHelper.readToken().isEmpty()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Maltabu.token = fileHelper.readToken();
            Maltabu.isAuth = "true";
        }
        epicDialog=new Dialog(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        arr = (ImageView) findViewById(R.id.arr);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAnimator.animateViewBound(arr);
                onBackPressed();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getPosting();
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MyScoreFragment fragment1 = new MyScoreFragment();
        MyPostsFragment fragment2 = new MyPostsFragment();
        MyProfileFragment fragment3 = new MyProfileFragment();
        if(Maltabu.lang.equals("ru")) {
            adapter.addFragment(fragment2, "Мои объявления");
            adapter.addFragment(fragment3, "Мои профиль");
            adapter.addFragment(fragment1, "Выписки по счету");
        } else {
            try {
                adapter.addFragment(fragment2,object.getString("Мои объявления"));
                adapter.addFragment(fragment3, object.getString("Мой профиль"));
                adapter.addFragment(fragment1, object.getString("Выписка по счету"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));
        overridePendingTransition( R.anim.slide_out_bottom,R.anim.slide_in_up);
        finish();
    }

    public void getPosting(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/cabinet/posting")
                .get()
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    fileHelper.writePostingFile(s1);
                    getUser();
                }
            }
        };
        asyncTask1.execute();
    }

    public void getUser(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/login/clients")
                .get()
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    fileHelper.writeUserFile(s1);
                    epicDialog.dismiss();
                }
            }
        };
        asyncTask1.execute();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
